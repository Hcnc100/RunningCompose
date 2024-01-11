package com.nullpointer.runningcompose.datasource.images.local

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
import timber.log.Timber
import java.io.File

class ImagesLocalDataSourceImpl(
    private val context: Context
) : ImagesLocalDataSource {
    /**
     * Saves an image to internal storage.
     *
     * @param fileImg The file to save.
     * @param nameFile The name of the file.
     * @return The absolute path of the saved file.
     * @throws Exception If an error occurs during saving.
     */
    override suspend fun saveToInternalStorage(
        fileImg: File,
        nameFile: String,
    ): String? = withContext(Dispatchers.IO) {
        try {
            context.openFileOutput(nameFile, Context.MODE_PRIVATE).use { output ->
                val bites = fileImg.readBytes()
                output.write(bites)
            }
            context.getFileStreamPath(nameFile).absolutePath
        } catch (e: Exception) {
            Timber.e("Error saved image $nameFile : $e")
            throw e
        }
    }

    /**
     * Deletes an image from storage.
     *
     * @param fullPath The full path of the image to delete.
     * @throws Exception If an error occurs during deletion.
     */
    override suspend fun deleterImgFromStorage(fullPath: String) {
        try {
            val nameFile = fullPath.split("/").last()
            context.deleteFile(nameFile)
        } catch (e: Exception) {
            Timber.e("Error deleter img $fullPath : $e")
            throw e
        }
    }

    /**
     * Compresses a bitmap image.
     *
     * @param bitmap The bitmap to compress.
     * @return The compressed file.
     * @throws Exception If an error occurs during compression.
     */
    override suspend fun compressBitmap(bitmap: Bitmap): File {
        return try {
            val fileCompress = Compress.with(context, bitmap)
                .setQuality(80)
                .concrete {
                    withMaxWidth(500f)
                    withMaxHeight(500f)
                    withScaleMode(ScaleMode.SCALE_HEIGHT)
                    withIgnoreIfSmaller(true)
                }.get(Dispatchers.IO)

            fileCompress
        } catch (e: Exception) {
            Timber.e("Error compressing bitmap : $e")
            throw e
        }
    }
}