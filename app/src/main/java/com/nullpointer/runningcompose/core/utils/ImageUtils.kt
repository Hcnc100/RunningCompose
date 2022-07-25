package com.nullpointer.runningcompose.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import timber.log.Timber
import java.io.File

object ImageUtils {
    fun saveToInternalStorage(
        bitmapImage: Bitmap,
        nameFile: String,
        context: Context,
    ): String? {
        return try {
            context.openFileOutput(nameFile, Context.MODE_PRIVATE).use { output ->
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, output)
            }
            nameFile
        } catch (e: Exception) {
            Timber.e("Error saved image $nameFile : $e")
            null
        }
    }

    fun saveToInternalStorage(
        fileImg: File,
        nameFile: String,
        context: Context,
    ): String? {
        return try {
            context.openFileOutput(nameFile, Context.MODE_PRIVATE).use { output ->
                val bites=fileImg.readBytes()
                output.write(bites)
            }
            context.getFileStreamPath(nameFile).absolutePath
        } catch (e: Exception) {
            Timber.e("Error saved image $nameFile : $e")
            null
        }
    }

    fun loadImageFromStorage(nameFile: String, context: Context): Bitmap? {
        return try {
            context.openFileInput(nameFile).use { input ->
                BitmapFactory.decodeStream(input)
            }
        } catch (e: Exception) {
            Timber.e("Error load img $nameFile : $e")
            null
        }
    }

    fun deleterImgFromStorage(nameFile:String,context: Context){
        try {
            context.deleteFile(nameFile)
        }catch (e:Exception){
            Timber.e("Error deleter img $nameFile : $e")
        }
    }
}