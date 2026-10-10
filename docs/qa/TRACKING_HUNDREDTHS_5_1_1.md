# Tracking stopwatch — 5.1.1

## Goal and constraint
Restore the fractional seconds removed from tracking without changing the renewed design. User considers fractional seconds essential while running and explicitly requested a publication push.

## Root cause and fix
The shared tracking presenter called `toFullFormatTime(false)`. It now uses `formatTrackingTime`, preserving the original `HH:mm:ss:cc` format (hundredths, not three-digit milliseconds) in portrait and landscape. Timer service, saved duration and notification cadence are unchanged.

Fractional digits use the existing `titleLarge` size beside the monospace `headlineMedium` whole seconds. This keeps the full timer on one line at 200% font scaling without hiding precision.

## Verification
- Four targeted JVM tests passed: zero, fractional precision, minute rollover, hours.
- Emulator Compose test passed: changes from `01:02:03:45` to `01:02:03:46`; full text displayed, single line, no width overflow at 200% font scale.
- Initial emulator run lost its Compose hierarchy while another app's test activity held focus. Retry exposed a genuine width overflow at 200%; smaller fractional type fixed it, and the final run passed without weakening assertions.
- Debug and instrumentation APK builds succeeded. Six offline release-tool tests passed.
- Screenshot: `tracking-hundredths/large-font.png` (production timer component in test activity).

## Files
- `app/src/main/java/com/nullpointer/runningcompose/ui/screens/tracking/componets/mapAndTime/MapAndTime.kt`: shared precision formatter.
- `app/src/main/java/com/nullpointer/runningcompose/ui/screens/tracking/componets/mapAndTime/TimeRun.kt`: fractional typography and preview.
- `app/src/test/java/com/nullpointer/runningcompose/ui/theme/TrackingTimeTest.kt`: formatter regression tests.
- `app/src/androidTest/java/com/nullpointer/runningcompose/ui/theme/RunningConsistencyTest.kt`: live recomposition and large-font regression.
- `app/build.gradle.kts`, `store/listings.json`: version 5.1.1 and bilingual release notes.

## Next steps
Push the targeted change and verify the existing automatic Play release workflow. Google's approval remains separate from successful submission.
