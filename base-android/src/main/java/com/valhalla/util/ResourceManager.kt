package com.valhalla.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Patterns
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Holder for android related calls so view models would still be testable.
 */
@Suppress("SpreadOperator")
class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileUtils: FileUtils,
    private val logger: Logger
) {
    @ColorInt
    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    fun getDimen(@DimenRes resId: Int) = context.resources.getDimension(resId)

    fun getInteger(@IntegerRes resId: Int) = context.resources.getInteger(resId)

    fun getAndroidPackageName() = context.applicationContext.packageName

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, quantity, *formatArgs)
    }

    /**
     * Returns Pair of scaled down image path and path of original image.
     *
     * @param imagePath path of image to scale down
     */
    suspend fun scaleDownImage(imagePath: String): Pair<String, String> {
        return if (imagePath.isBlank()) {
            Pair("", imagePath)
        } else {
            val photoUri = fileUtils.scaleDownPhoto(
                "file:$imagePath",
                context,
                PHOTO_MAX_DIMENSION,
                PHOTO_COMPRESSION_QUALITY,
                false
            )

            try {
                Pair(photoUri.path!!, imagePath)
            } catch (e: Exception) {
                logger.e(e, "Error scaling down photo: ${e.message}")
                Pair("", imagePath)
            }
        }
    }

    /**
     * Returns scaled down image if scaling down succeeds. Else, returns original image.
     *
     * @param imagePath path of image to scale down
     */
    suspend fun scaleDownImageToFile(imagePath: String): File {
        return if (imagePath.isBlank()) {
            throw IllegalArgumentException("imagePath is blank.")
        } else {
            val photoUri = fileUtils.scaleDownPhoto(
                "file:$imagePath",
                context,
                PHOTO_MAX_DIMENSION,
                PHOTO_COMPRESSION_QUALITY,
                false
            )
            try {
                File(photoUri.path!!)
            } catch (e: Exception) {
                logger.e(e, "Error scaling down photo: ${e.message}")
                File(imagePath)
            }
        }
    }

    /**
     * Returns device's country name code (i.e. "ph", "au", "cn").
     */
    fun getDeviceCountryNameCode(): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkCountryIso
    }

    companion object {
        private const val PHOTO_MAX_DIMENSION = 1080f
        private const val PHOTO_COMPRESSION_QUALITY = 100
    }
}
