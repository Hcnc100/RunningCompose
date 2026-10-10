package com.nullpointer.runningcompose.ui.theme

import com.nullpointer.runningcompose.ui.screens.tracking.componets.mapAndTime.formatTrackingTime
import org.junit.Assert.assertEquals
import org.junit.Test

class TrackingTimeTest {
    @Test fun zeroIncludesHundredths() = assertEquals("00:00:00:00", formatTrackingTime(0))
    @Test fun subsecondPrecisionIsPreserved() = assertEquals("00:00:00:12", formatTrackingTime(129))
    @Test fun secondsRollOverWithoutLosingFractions() = assertEquals("00:01:00:01", formatTrackingTime(60_010))
    @Test fun hoursAndFractionsArePreserved() = assertEquals("01:02:03:45", formatTrackingTime(3_723_450))
}
