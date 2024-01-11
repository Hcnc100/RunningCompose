package com.nullpointer.runningcompose.datasource.images.local

import android.graphics.Bitmap
import java.io.File

interface ImagesLocalDataSource {

    suspend fun saveToInternalStorage(
        fileImg: File,
        nameFile: String,
    ): String?

    suspend fun deleterImgFromStorage(fullPath: String)

    suspend fun compressBitmap(bitmap: Bitmap): File
}