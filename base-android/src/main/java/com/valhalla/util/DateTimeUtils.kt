package com.valhalla.util

import com.valhalla.data.Constants
import org.threeten.bp.Duration
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.TimeZone
import kotlin.Exception
import kotlin.jvm.Throws

fun formatDate(
    date: String,
    parseFormat: String = Constants.DATE_TIME_FORMAT1,
    outputFormat: String = Constants.DATE_FORMAT1
): String {
    val format = SimpleDateFormat(parseFormat, Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    val dateFormat = try {
        format.parse(date)
    } catch (e: ParseException) {
        Date()
    }
    val sdf = SimpleDateFormat(outputFormat, Locale.US)
    return sdf.format(dateFormat!!)
}

fun getDuration(millis: Long): String {
    val duration = Duration.ofMillis(millis)
    return if (duration.toHours() > 0) String.format(
        "%d:%02d:%02d",
        duration.toHours(),
        duration.toMinutes() - (duration.toHours() * 60),
        duration.seconds - (duration.toMinutes() * 60)
    )
    else String.format(
        "%d:%02d",
        duration.toMinutes(),
        duration.seconds - (duration.toMinutes() * 60)
    )
}

@Throws(Exception::class)
fun String.formatDate(dateParser: SimpleDateFormat, dateFormatter: SimpleDateFormat): String {
    val date = dateParser.parse(this)
    return dateFormatter.format(date!!)
}
