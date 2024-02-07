package com.nullpointer.runningcompose.domain.runs

import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSource
import com.nullpointer.runningcompose.datasource.images.local.ImagesLocalDataSource
import com.nullpointer.runningcompose.datasource.run.local.RunsLocalDataSource
import com.nullpointer.runningcompose.models.data.DistanceListPolyline
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


class RunRepoImpl(
    private val configLocalDataSource: ConfigLocalDataSource,
    private val runsLocalDataSource: RunsLocalDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource
) : RunRepository {

    override val countRuns: Flow<Int> = runsLocalDataSource.getCountRun()

    override val listRunsOrderByDate: Flow<List<RunData>> = configLocalDataSource.settingsData
        .map { settingsData -> settingsData.numberRunsGraph }
        .distinctUntilChanged()
        .flatMapLatest { numberRunsGraph ->
            runsLocalDataSource.getListOrderByDate(numberRunsGraph)
        }

    override val totalStatisticRuns: Flow<StatisticsRun> =
        runsLocalDataSource.totalStatisticRuns

    override suspend fun deleterListRuns(listIds: List<Long>) {
        runsLocalDataSource.getListRunsById(listIds).forEach {
            it.pathImgRun?.let { imagesLocalDataSource::deleterImgFromStorage }
        }
        runsLocalDataSource.deleterListRuns(listIds)
    }

    override suspend fun deleterRun(runData: RunData) =
        runsLocalDataSource.deleterRun(runData)

    override suspend fun insertNewRun(
        bitmap: Bitmap?,
        timeRunInMillis: Long,
        listPoints: List<List<LatLng>>,
    ) = coroutineScope {

        val weightUser = authLocalDataSource.getUserData().first()!!.weight
        val mapConfig = configLocalDataSource.settingsData.first().mapConfig
        val createAt = System.currentTimeMillis()

        val compressImageAndSavedTask = bitmap?.let {
            async { compressImageAndSaved(imageMap = bitmap, createAt = createAt) }
        }

        val distanceListPolylineDeferred =
            async { DistanceListPolyline.fromListPolyline(listPoints) }

        val (distanceInMeters, listPolylineEncode) = distanceListPolylineDeferred.await()

        val avgSpeedInMS = distanceInMeters / (timeRunInMillis / 1000f)
        val caloriesBurned = distanceInMeters * (weightUser / 1000f)

        val pathImageCompress = compressImageAndSavedTask?.await()

        val runData = RunData(
            createAt = createAt,
            mapConfig = mapConfig,
            timeRunInMillis = timeRunInMillis,
            caloriesBurned = caloriesBurned,
            distanceInMeters = distanceInMeters,
            pathImgRun = pathImageCompress,
            avgSpeedInMeters = avgSpeedInMS,
            listPolyLineEncode = listPolylineEncode
        )
        runsLocalDataSource.insertNewRun(runData)
    }


    private suspend fun compressImageAndSaved(
        createAt: Long,
        imageMap: Bitmap,
    ): String? {
        val imageCompress = imagesLocalDataSource.compressBitmap(imageMap)
        val imageSaved = imagesLocalDataSource.saveToInternalStorage(
            nameFile = "img-run-$createAt.jpg",
            fileImg = imageCompress
        )
        imageCompress.delete()
        return imageSaved
    }

    override fun getAllListRunOrdered(
        sortType: SortType, isReverse: Boolean
    ): PagingSource<Int, RunEntity> = runsLocalDataSource.getListForTypeSort(sortType, isReverse)

}