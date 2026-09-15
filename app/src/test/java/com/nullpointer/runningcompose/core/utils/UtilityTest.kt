import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.core.utils.Utility
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilityTest {

    companion object {
        val pointA = LatLng(0.0, 0.0)
        val pointB = LatLng(0.0, 1.0)
        val pointC = LatLng(1.0, 1.0)

        val listPoints = listOf(
            pointA,
            pointB,
            pointC
        )
    }

    @Test
    fun `calculatePolylineLength returns correct distance for valid input`() {
        val result = Utility.calculatePolylineLength(listPoints)

        // Two one-degree segments are approximately 222.4 km at the equator.
        assertEquals(222_390f, result, 100f)
    }

    @Test
    fun `calculatePolylineLength returns zero for empty input`() {
        val emptyListPoints = emptyList<LatLng>()
        val result = Utility.calculatePolylineLength(emptyListPoints)

        assertEquals(0f, result, 0.001F)
    }

    @Test
    fun `calculatePolylineLength returns zero for single point input`() {
        val polyline = listOf(pointA)
        val result = Utility.calculatePolylineLength(polyline)

        assertEquals(0f, result, 0.001F)
    }
}
