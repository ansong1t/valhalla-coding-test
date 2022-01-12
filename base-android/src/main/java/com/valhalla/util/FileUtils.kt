package com.valhalla.util

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class FileUtils @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {
    fun getUrlForResource(
        resourceId: Int,
        packageName: String
    ): String {
        return Uri.parse("android.resource://$packageName/$resourceId")
            .toString()
    }

    fun saveBitmap(
        bitmap: Bitmap,
        filename: String,
        quality: Int,
        recycle: Boolean
    ): Uri {
        val fileOutputStream: FileOutputStream
        val photo = File(context.filesDir.path, filename)
        val mimeType = getMimeType(context, Uri.parse(photo.absolutePath))
        logger.d("mimeType: %s", mimeType)

        try {
            fileOutputStream = FileOutputStream(photo)

            val bos = BufferedOutputStream(fileOutputStream)
            var compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG

            if (mimeType.isNotBlank()) {
                val lowerMimeType = mimeType.toLowerCase(Locale.getDefault())

                if (lowerMimeType.contains("png")) {
                    compressFormat = Bitmap.CompressFormat.PNG
                } else if (lowerMimeType.contains("webp")) {
                    compressFormat = Bitmap.CompressFormat.WEBP
                }
            }

            bitmap.compress(compressFormat, quality, bos)
            logger.d("photo saved: %s", photo.toString())

            try {
                bos.flush()
                bos.close()
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: IOException) {
                logger.e(e, "Error in cleaning up streams: %s", filename)
            }
        } catch (e: IOException) {
            logger.e(e, "IOException in saving bitmap: %s", filename)
        } catch (e: Exception) {
            logger.e(e, "Error in saving bitmap: %s", filename)
        }

        if (recycle) {
            bitmap.recycle()
        }

        return Uri.parse("file:" + photo.absolutePath)
    }

    fun getMimeType(
        context: Context,
        uri: Uri
    ): String {
        var mimeType: String?

        if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
            val cr = context.contentResolver
            mimeType = cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase(Locale.getDefault())
            )

            if (mimeType.isNullOrEmpty()) {
                mimeType = if ("json" == fileExtension) {
                    "application/json"
                } else {
                    "text/$fileExtension"
                }
            }
        }

        return mimeType ?: ""
    }

    suspend fun deleteFile(filePath: String): Boolean {
        val path = filePath.replaceFirst("[^:]+:".toRegex(), "")

        logger.d("Deleting file: %s", path)

        val fileToDelete = File(path)
        return fileToDelete.delete()
    }

    suspend fun scaleDownBitmap(bitmap: Bitmap, maxImageSize: Float, filter: Boolean): Bitmap {
        if (bitmap.width < maxImageSize && bitmap.height < maxImageSize) {
            return bitmap
        }

        val ratio = Math.min(maxImageSize / bitmap.width, maxImageSize / bitmap.height)
        val width = (ratio * bitmap.width).roundToInt()
        val height = (ratio * bitmap.height).roundToInt()

        return Bitmap.createScaledBitmap(bitmap, width, height, filter)
    }

    suspend fun scaleDownPhoto(
        photoPath: String,
        context: Context,
        maxDimension: Float,
        compressionQuality: Int,
        filter: Boolean
    ): Uri {
        logger.d("resizePhoto start --> path: %s", photoPath)

        val photoUri = Uri.parse(photoPath)
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(photoUri)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        if (bitmap.width < maxDimension && bitmap.height < maxDimension) {
            return photoUri
        } else {
            val ratio = Math.min(maxDimension / bitmap.width, maxDimension / bitmap.height)
            val width = (ratio * bitmap.width).roundToInt()
            val height = (ratio * bitmap.height).roundToInt()
            val scaledDownBitmap = Bitmap.createScaledBitmap(bitmap, width, height, filter)
            val scaledDownUri = saveBitmap(
                scaledDownBitmap,
                photoUri.lastPathSegment!!,
                compressionQuality,
                true
            )

            logger.d(
                "resizePhotos finish --> path: %s, scaledDown: %s",
                photoUri,
                scaledDownUri
            )

            return scaledDownUri
        }
    }

    fun getOutputDirectory(appName: String): String {
        val appContext = context.applicationContext
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(
                it, appName
            ).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir.absolutePath
        else appContext.filesDir.absolutePath
    }

    fun deleteTempFiles(appName: String) {
        File(getOutputDirectory(appName))
            .walk()
            .filter { it.name.contains(TEMP_FILE_PREFIX) }
            .forEach { file ->
                if (file.extension == PHOTO_EXTENSION) {
                    deleteFileFromMediaStore(file, context)
                } else {
                    file.delete()
                }
            }
    }

    private fun deleteFileFromMediaStore(file: File, context: Context) {
        // Set up the projection (we only need the ID)
        val projection =
            arrayOf<String>(MediaStore.Images.Media._ID)

        // Match on the file path
        val selection: String = MediaStore.Images.Media.DATA + " = ?"
        val selectionArgs =
            arrayOf<String>(file.absolutePath)

        // Query for the ID of the media matching the file path
        val queryUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val c: Cursor? =
            context.contentResolver.query(
                queryUri,
                projection,
                selection,
                selectionArgs,
                null
            )

        c?.let {
            // We found the ID. Deleting the item via the content provider will also remove the file
            if (c.moveToFirst()) {
                val id: Long =
                    c.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))

                val deleteUri: Uri =
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                context.contentResolver.delete(deleteUri, null, null)
            }

            c.close()
        }
    }

    companion object {
        const val TEMP_FILE_PREFIX = "valhalla_temp_"
        const val PHOTO_EXTENSION = "jpg"
    }
}
