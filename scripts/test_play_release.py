import json
import unittest
from play_release import ROOT, next_code, validate_listings


class VersionCodeTest(unittest.TestCase):
    def test_exceeds_every_track_and_bundle(self):
        self.assertEqual(next_code([10, 1008, 2000000000], 1700000000), 2000000001)

    def test_retry_uses_new_code(self):
        self.assertEqual(next_code([1700000000], 1700000001), 1700000001)

    def test_same_second_still_increments(self):
        self.assertEqual(next_code([1700000000], 1700000000), 1700000001)

    def test_limit_fails_closed(self):
        with self.assertRaises(ValueError):
            next_code([2100000000], 1700000000)

    def test_listing_limits(self):
        validate_listings(json.loads((ROOT / 'store/listings.json').read_text(encoding='utf-8')))

    def test_invalid_description_rejected(self):
        with self.assertRaises(ValueError):
            validate_listings({'en-US': {'title': 'X' * 31}})


if __name__ == '__main__':
    unittest.main()
