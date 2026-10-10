# RunningApp 5.1.0 — release handoff

## Goal
Publish the Material 3 UI improvements, bilingual store copy and refreshed screenshots to the existing `com.nullpointer.running` application.

## Verified baseline
- Play Console access confirmed; published production release: **5.0.1 (1008)**, available at 100%, five users (2026-10-10).
- Do not use `com.nullpointer.runningcompose`: that is a separate draft application.
- GitHub repository `Hcnc100/RunningCompose`, main branch; authenticated push dry-run succeeded.
- Local `testDebugUnitTest bundleRelease`: successful. Local release uses development signing; only CI's production signing is eligible for upload.
- Six release-tool unit tests passed (version selection, retry, limit, listing lengths). Earlier UI/instrumentation results and limitations are in `docs/qa/`.

## Strategy
- Keep existing automatic production deployment on pushes to main and manual workflow dispatch.
- Serialize complete build/deploy jobs; never cancel an active upload.
- Query all visible bundles, APKs and tracks before building. Select `max(current Unix seconds, every existing code + 1)` and reject values above Play's 2,100,000,000 limit. Recheck before upload to fail safely on out-of-band publication.
- Rerun the **whole workflow**, not just an upload step, if a version was already uploaded. Build retries use a new code; never blindly retry a commit with an ambiguous network result.
- One Play edit updates the signed bundle, production track, notes, descriptions and phone screenshots. Validate before committing; do not silently fall back to draft or suppress review errors.
- Keep 100% rollout: the current audience is only five users, so a 10% rollout provides little meaningful coverage. Review is still controlled by Google.
- English (`en-US`) and Latin American Spanish (`es-419`) assets are versioned in `store/`; existing other Spanish listings receive the Spanish update. Other listing assets, videos and unrelated languages are preserved.
- Screenshots are app/emulator captures, not generated mockups. Tracking coordinates are simulated at Google's Mountain View campus, not a user's private route; no fabricated runs are saved. Introduction capture demonstrates the production composable in a test activity.

## Publication verification
`play-release-result.json` is uploaded as a CI artifact after a post-commit track check. **Track status `completed` and a green workflow do not establish that Google approved the review or that the release is live.** Check Publishing overview and Production in Play Console for the final visible state.

## Relevant files
- `.github/workflows/main.yml`: signing, tests, version preflight, bundle and transactional publication.
- `scripts/play_release.py`: Google Publisher API operations, safe version selection and post-commit verification; credentials only from CI environment.
- `scripts/test_play_release.py`: offline release guards.
- `store/listings.json`: English/Spanish descriptions and release notes.
- `store/screenshots/`: refreshed localized phone screenshots.

## Next steps
Run the workflow after committing, inspect API/CI outcome, then verify review/live status in Console. Record the actual version code and outcome below; never mark publication complete while review is pending.

## Executed outcome (2026-10-10)
- Commit `95e3099` pushed successfully to `origin/main`.
- Workflow https://github.com/Hcnc100/RunningCompose/actions/runs/38063915350 completed successfully: unit tests, signed release bundle, listings and production submission.
- API preflight maximum was 1008; new accepted versionCode **1791646262**, versionName **5.1.0**. No duplicate-version error.
- API confirmed three phone screenshots and copy per language (`en-US`, `es-419`); post-commit read confirmed production release.
- Console showed **Cambios en la etapa de revisión**, with automated checks running and automatic submission after successful checks. Managed publishing is disabled. This is **not yet a live-release confirmation**.
- Evidence: `docs/qa/play-release/play-console-review.jpg`, `play-release-result.json`.
- Next action: wait for Google's checks/review; if approved, publication is automatic. If rejected, inspect the specific policy/quality finding before another release. Do not resend the same AAB or reuse its versionCode.
- Rechecked after waiting 13 minutes: automated-check countdown disappeared and Console now states **"Tus cambios están en proceso de revisión"**. No version-conflict failure appeared. Google review remains the external blocker to confirming public availability.
