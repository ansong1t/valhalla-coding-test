package com.valhalla.data.util

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun String.fromIsoDateTimeToInstant(): Instant {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        .atZone(ZoneId.systemDefault())
        .toInstant()
}
