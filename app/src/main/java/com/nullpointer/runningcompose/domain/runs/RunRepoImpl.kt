package com.nullpointer.runningcompose.domain.runs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nullpointer.runningcompose.core.utils.ImageUtils
import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSource
import com.nullpointer.runningcompose.data.local.datasource.runs.RunsLocalDataSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
import me.shouheng.compress.utils.copyTo


@OptIn(ExperimentalCoroutinesApi::class)
class RunRepoImpl(
    configLocalDataSource: ConfigLocalDataSource,
    private val runsLocalDataSource: RunsLocalDataSource,
    private val context: Context
) : RunRepository {


    override val listRunsOrdered: Flow<List<Run>> = configLocalDataSource.sortConfig.flatMapLatest {
        runsLocalDataSource.getListForTypeSort(it.sortType)
            .map { list -> if (it.isReverse) list.reversed() else list }
    }

    override val listRunsOrderByDate: Flow<List<Run>> =
        runsLocalDataSource.getListForTypeSort(SortType.DATE)

    override val totalStatisticRuns: Flow<StatisticsRun> =
        runsLocalDataSource.totalStatisticRuns

    override suspend fun deleterListRuns(listIds: List<Long>) {
        runsLocalDataSource.getListRunsById(listIds).forEach {
            it.pathImgRun?.let { pathImg -> ImageUtils.deleterImgFromStorage(pathImg, context) }
        }
        runsLocalDataSource.deleterListRuns(listIds)

    }

    override suspend fun deleterRun(run: Run) =
        runsLocalDataSource.deleterRun(run)

    override suspend fun insertNewRun(run: Run, bitmap: Bitmap?) {
        val fileCompress=bitmap?.let {
            Compress.with(context, it)
                .setQuality(80)
                .concrete {
                    withMaxWidth(500f)
                    withMaxHeight(500f)
                    withScaleMode(ScaleMode.SCALE_HEIGHT)
                    withIgnoreIfSmaller(true)
                }.get(Dispatchers.IO)
        }
        val nameFile = "img-map-${run.timestamp}.png"
        val pathImg = fileCompress?.let { ImageUtils.saveToInternalStorage(it, nameFile, context) }
        fileCompress?.delete()
        runsLocalDataSource.insertNewRun(run.copy(pathImgRun = pathImg))
    }

}