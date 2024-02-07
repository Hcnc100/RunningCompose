import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.nullpointer.runningcompose.core.utils.Utility
import com.nullpointer.runningcompose.models.data.DistanceListPolyline
import org.junit.Assert.assertEquals
import org.junit.Test

class DistanceListPolylineTest {

    @Test
    fun `fromListPolyline returns correct distance and encoded polyline for valid input`() {
        val listPoints = listOf(
            listOf(LatLng(0.0, 0.0), LatLng(0.0, 1.0)),
            listOf(LatLng(1.0, 1.0), LatLng(1.0, 2.0))
        )
        val result = DistanceListPolyline.fromListPolyline(listPoints)

        assertEquals(
            2 * Utility.calculatePolylineLength(listPoints[0]),
            result.distanceInMeters,
            0.001F
        )
        assertEquals(listPoints.map { PolyUtil.encode(it) }, result.listEncodePolyline)
    }


    @Test
    fun `fromListPolyline returns zero distance and empty polyline for empty input`() {
        val listPoints = emptyList<List<LatLng>>()
        val result = DistanceListPolyline.fromListPolyline(listPoints)

        assertEquals(0f, result.distanceInMeters, 0.001F)
        assertEquals(emptyList<String>(), result.listEncodePolyline)
    }

    @Test
    fun `fromListPolyline ignores sublists with less than two points`() {
        val listPoints = listOf(
            listOf(LatLng(0.0, 0.0)),
            listOf(LatLng(1.0, 1.0), LatLng(1.0, 2.0))
        )
        val result = DistanceListPolyline.fromListPolyline(listPoints)

        assertEquals(
            Utility.calculatePolylineLength(listPoints[1]),
            result.distanceInMeters,
            0.001F
        )
        assertEquals(listOf(PolyUtil.encode(listPoints[1])), result.listEncodePolyline)
    }
}