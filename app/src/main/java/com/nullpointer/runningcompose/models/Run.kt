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
    val timestamp:Long=System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Long=0
) : Parcelable{

    @IgnoredOnParcel
    @Ignore
    var isSelected:Boolean=false

    companion object{
        fun generateFake()=Run(
            nameFileImg = "",
            avgSpeed = 100F,
            distance = 100F,
            timeRunInMillis = (13000..50000).random().toLong(),
            caloriesBurned = 1500F,
            listPolyLineEncode = emptyList()
        )
    }
}