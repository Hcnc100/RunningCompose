package com.nullpointer.runningcompose.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "run_table")
@Parcelize
data class Run(
    val nameFileImg:String,
    val avgSpeed:Float,
    val distance:Float,
    val timeRunInMillis:Long,
    val caloriesBurned:Float,
    val listPolyLineEncode:List<String>,
    val timeStamp:Long=System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Long?=null
) : Parcelable{

    @IgnoredOnParcel
    @Ignore
    var isSelected:Boolean=false
}