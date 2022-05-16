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
    val nameFileImg:String="",
    val avgSpeed:Float=1f,
    val distance:Float=2f,
    val timeRunInMillis:Long=2L,
    val caloriesBurned:Float=2f,
    val listPolyLineEncode:List<String> = emptyList(),
    val timestamp:Long=System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Long?=null
) : Parcelable{

    @IgnoredOnParcel
    @Ignore
    var isSelected:Boolean=false
}