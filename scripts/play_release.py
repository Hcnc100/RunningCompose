"""Transactional Play release. Credentials are provided only by CI, never stored."""
import argparse
import json
import os
from pathlib import Path
import re
import time
import urllib.error
import urllib.request

PACKAGE = "com.nullpointer.running"
ROOT = Path(__file__).resolve().parents[1]
VERSION_NAME = re.search(r'versionName\s*=\s*"([^"]+)"', (ROOT / "app/build.gradle.kts").read_text(encoding="utf-8")).group(1)
API = "https://androidpublisher.googleapis.com/androidpublisher/v3/applications/" + PACKAGE
UPLOAD = "https://androidpublisher.googleapis.com/upload/androidpublisher/v3/applications/" + PACKAGE


def next_code(existing, now):
    code = max([int(now)] + [int(x) + 1 for x in existing])
    if not 0 < code <= 2100000000:
        raise ValueError("Play versionCode limit exceeded")
    return code


def validate_listings(listings):
    for language, listing in listings.items():
        for field, limit in (("title", 30), ("shortDescription", 80), ("fullDescription", 4000), ("releaseNotes", 500)):
            if not 0 < len(listing[field]) <= limit:
                raise ValueError(f"Invalid {language} {field} length")


class Publisher:
    def __init__(self):
        import google.auth.transport.requests
        from google.oauth2 import service_account
        self.credentials = service_account.Credentials.from_service_account_info(
            json.loads(os.environ["GOOGLE_PLAY_SERVICE_ACCOUNT_JSON"]),
            scopes=["https://www.googleapis.com/auth/androidpublisher"],
        )
        self.transport = google.auth.transport.requests.Request()

    def request(self, method, path, body=None, content_type="application/json", upload=False):
        if not self.credentials.valid:
            self.credentials.refresh(self.transport)
        data = json.dumps(body).encode() if isinstance(body, dict) else body
        url = (UPLOAD if upload else API) + path
        request = urllib.request.Request(url, data=data, method=method, headers={
            "Authorization": "Bearer " + self.credentials.token,
            "Content-Type": content_type,
        })
        try:
            with urllib.request.urlopen(request, timeout=300) as response:
                raw = response.read()
                return json.loads(raw) if raw else {}
        except urllib.error.HTTPError as error:
            # API error bodies contain diagnostics, not request credentials.
            detail = json.loads(error.read()).get("error", {}).get("message", "HTTP error")
            raise RuntimeError(f"Play API {error.code}: {detail}") from None

    def edit(self):
        return self.request("POST", "/edits", {})["id"]

    def codes(self, edit):
        base = f"/edits/{edit}"
        bundles = self.request("GET", base + "/bundles").get("bundles", [])
        apks = self.request("GET", base + "/apks").get("apks", [])
        tracks = self.request("GET", base + "/tracks").get("tracks", [])
        codes = [int(x["versionCode"]) for x in bundles + apks]
        codes += [int(c) for t in tracks for r in t.get("releases", []) for c in r.get("versionCodes", [])]
        return codes


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("command", choices=["preflight", "publish"])
    parser.add_argument("--bundle")
    parser.add_argument("--code", type=int)
    args = parser.parse_args()
    listings = json.loads((ROOT / "store/listings.json").read_text(encoding="utf-8"))
    validate_listings(listings)
    publisher = Publisher()
    edit = publisher.edit()
    base = f"/edits/{edit}"
    committed = False
    try:
        existing = publisher.codes(edit)
        if args.command == "preflight":
            code = next_code(existing, time.time())
            print(f"Play maximum versionCode: {max(existing, default=0)}; reserved build code: {code}")
            with open(os.environ["GITHUB_OUTPUT"], "a", encoding="utf-8") as output:
                output.write(f"version_code={code}\n")
            return
        if not args.bundle or not args.code or args.code <= max(existing, default=0):
            raise ValueError("Bundle/code missing or versionCode already used; build a new release")
        uploaded = publisher.request("POST", base + "/bundles?uploadType=media", Path(args.bundle).read_bytes(), "application/octet-stream", upload=True)
        if int(uploaded["versionCode"]) != args.code:
            raise ValueError("Bundle manifest versionCode differs from preflight")
        # Update each existing Spanish locale too; preserve video/other listing fields.
        current = publisher.request("GET", base + "/listings").get("listings", [])
        languages = set(listings) | {x["language"] for x in current if x["language"].startswith("es-")}
        for language in sorted(languages):
            source = "es-419" if language.startswith("es-") else language
            listing = listings[source]
            previous = next((x for x in current if x["language"] == language), {})
            body = {k: v for k, v in previous.items() if k in ("title", "shortDescription", "fullDescription", "video")}
            body.update({k: listing[k] for k in ("title", "shortDescription", "fullDescription")})
            publisher.request("PUT", base + f"/listings/{language}", body)
            images = sorted((ROOT / "store/screenshots" / source).glob("*.png"))
            if len(images) < 2:
                raise ValueError(f"At least two screenshots required for {language}")
            publisher.request("DELETE", base + f"/listings/{language}/phoneScreenshots")
            for image in images:
                publisher.request("POST", base + f"/listings/{language}/phoneScreenshots?uploadType=media", image.read_bytes(), "image/png", upload=True)
            print(f"Listing {language}: updated text and {len(images)} phone screenshots")
        release = {"name": f"{VERSION_NAME} ({args.code})", "versionCodes": [str(args.code)], "status": "completed",
                   "releaseNotes": [{"language": language, "text": listings["es-419" if language.startswith("es-") else language]["releaseNotes"]} for language in sorted(languages)]}
        publisher.request("PUT", base + "/tracks/production", {"track": "production", "releases": [release]})
        publisher.request("POST", base + ":validate", {})
        publisher.request("POST", base + ":commit", {})
        committed = True
        print(f"Committed production release {args.code}. Google review/publication must be verified in Play Console.")
        verify = publisher.edit()
        try:
            track = publisher.request("GET", f"/edits/{verify}/tracks/production")
            if not any(str(args.code) in r.get("versionCodes", []) for r in track.get("releases", [])):
                raise RuntimeError("Post-commit verification did not find the uploaded release")
            summary = {"versionCode": args.code, "versionName": VERSION_NAME, "track": track, "listings": sorted(languages), "publication": "Submitted; verify review/live state in Play Console"}
            Path("play-release-result.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
            print(json.dumps(summary))
        finally:
            publisher.request("DELETE", f"/edits/{verify}")
    finally:
        if not committed:
            publisher.request("DELETE", base)


if __name__ == "__main__":
    main()
