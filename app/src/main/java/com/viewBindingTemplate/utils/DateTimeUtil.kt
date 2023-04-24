package com.viewBindingTemplate.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DD_MM_YYYY = "dd/MM/yyyy"
const val MM_DD_YYYY = "MM/dd/yyyy"
const val YYYY_MM_DD = "yyyy-MM-dd"

fun Context.datePickerDialog(
    isMaxCalendar: Boolean = false,
    isMinCalendar: Boolean = false,
    minimumDayInMillis: Long = System.currentTimeMillis(),
    maximumDayInMillis: Long = System.currentTimeMillis(),
    returnDate: (Calendar) -> Unit,
) {
    val calender: Calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            returnDate(calender)
        },
        calender.get(Calendar.YEAR),
        calender.get(Calendar.MONTH),
        calender.get(Calendar.DAY_OF_MONTH)
    )
    if (isMaxCalendar) datePicker.datePicker.maxDate = maximumDayInMillis
    if (isMinCalendar) datePicker.datePicker.minDate = minimumDayInMillis
    datePicker.show()
}

fun Long.formatTo(outputFormat: String = UTC_FORMAT): String? {
    return try {
        SimpleDateFormat(outputFormat, Locale.ENGLISH).format(Date(this))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}