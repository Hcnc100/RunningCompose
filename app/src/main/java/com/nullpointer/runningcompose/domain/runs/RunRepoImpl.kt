package com.nullpointer.runningcompose.domain.runs

import android.content.Context
import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.nullpointer.runningcompose.core.utils.ImageUtils
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSource
import com.nullpointer.runningcompose.datasource.run.local.RunsLocalDataSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode


class RunRepoImpl(
    private val configLocalDataSource: ConfigLocalDataSource,
    private val runsLocalDataSource: RunsLocalDataSource,
    private val context: Context
) : RunRepository {

    override val countRuns: Flow<Int> = runsLocalDataSource.getCountRun()

    override val listRunsOrderByDate: Flow<List<Run>> =
        runsLocalDataSource.getListOrderByDate(10)

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
        val fileCompress = bitmap?.let {
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

    override fun getAllListRunOrdered(
        sortType: SortType,isReverse:Boolean
    ): PagingSource<Int, Run> {
        return runsLocalDataSource.getListForTypeSort(sortType,isReverse)
    }

}