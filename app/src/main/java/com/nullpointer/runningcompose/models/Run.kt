package com.nullpointer.runningcompose.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.nullpointer.runningcompose.models.config.MapConfig
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "run_table")
@Parcelize
data class Run(
    val avgSpeedInMeters:Float,
    val distanceInMeters:Float,
    val timeRunInMillis:Long,
    val caloriesBurned:Float,
    val listPolyLineEncode:List<String>,
    val timestamp:Long=System.currentTimeMillis(),
    @Embedded
    val mapConfig:MapConfig,
    @PrimaryKey(autoGenerate = true)
    val id:Long=0
) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    var isSelected:Boolean=false

}