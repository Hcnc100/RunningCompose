import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.entities.RunEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class RunDataTest {

    @Test
    fun `fromRunEntity correctly converts RunEntity to RunData`() {
        val runEntity = RunEntity(
            id = 1,
            mapConfig = MapConfig(),
            timestamp = 100023,
            pathImgRun = null,
            caloriesBurned = 100F,
            timeRunInMillis = 53000,
            avgSpeedInMeters = 150F,
            distanceInMeters = 2500F,
            listPolyLineEncode = emptyList()
        )

        val expectedRunData = RunData(
            id = 1,
            mapConfig = MapConfig(),
            createAt = 100023,
            pathImgRun = null,
            caloriesBurned = 100F,
            timeRunInMillis = 53000,
            avgSpeedInMeters = 150F,
            distanceInMeters = 2500F,
            listPolyLineEncode = emptyList()
        )

        val actualRunData = RunData.fromRunEntity(runEntity)

        assertEquals(expectedRunData, actualRunData)
    }
}