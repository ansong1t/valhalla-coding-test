package com.valhalla.extensions

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.valhalla.data.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

inline fun showDatePicker(
    context: Context,
    currentDate: String,
    minDate: String? = null,
    maxDate: String? = null,
    format: String = Constants.DATE_FORMAT1,
    crossinline onDateSet: (view: DatePicker, year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val formatter = SimpleDateFormat(format, Locale.US)
    val currentDateFormat = try {
        formatter.parse(currentDate)
    } catch (e: ParseException) {
        Date()
    }
    val maxDateFormat = maxDate?.let {
        try {
            formatter.parse(maxDate)
        } catch (e: ParseException) {
            Date()
        }
    }
    val minDateFormat = minDate?.let {
        try {
            formatter.parse(minDate)
        } catch (e: ParseException) {
            Date()
        }
    }
    showDatePicker(
        context,
        currentDateFormat,
        minDateFormat,
        maxDateFormat ?: Date(),
        onDateSet
    )
}

inline fun showDatePicker(
    context: Context,
    date: Date? = null,
    minDate: Date? = null,
    maxDate: Date = Date(),
    crossinline onDateSet: (view: DatePicker, year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val calBefore: Calendar = Calendar.getInstance()
    date?.let { calBefore.time = date }
    val dialog = DatePickerDialog(
        context,
        { v, year, monthOfYear, dayOfMonth ->
            onDateSet(v, year, monthOfYear, dayOfMonth)
        },
        calBefore.get(Calendar.YEAR),
        calBefore.get(Calendar.MONTH),
        calBefore.get(Calendar.DAY_OF_MONTH)
    )
    minDate?.let {
        dialog.datePicker.minDate = minDate.time
    }
    dialog.datePicker.maxDate = maxDate.time

    dialog.show()
}
