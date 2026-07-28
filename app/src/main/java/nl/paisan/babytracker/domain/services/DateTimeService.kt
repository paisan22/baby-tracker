package nl.paisan.babytracker.domain.services

import android.content.Context
import nl.paisan.babytracker.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Context.getDateTime(datetime: Long) : String {
    val pattern = this.getString(R.string.dd_mm_yyyy_hh_mm)
    val language = getString(R.string.language)
    
    val dateFormat =
        SimpleDateFormat(
            pattern,
            Locale(language, language.uppercase())
        )

    return dateFormat.format(Date(datetime))
}

fun Context.getTime(datetime: Long) : String {
    val pattern = this.getString(R.string.hh_mm_ss)
    val language = getString(R.string.language)

    val dateFormat =
        SimpleDateFormat(
            pattern,
            Locale(language, language.uppercase())
        )

    return dateFormat.format(Date(datetime))
}

fun millisToMinutesSeconds(deltaMillis: Long): Pair<Int, Int> {
    val deltaSeconds = deltaMillis / 1000
    return (deltaSeconds / 60).toInt() to (deltaSeconds % 60).toInt()
}

fun Context.getDelta(startDate: Long, endDate: Long): String {
    val (minutes, seconds) = millisToMinutesSeconds(endDate - startDate)
    return "%02d:%02d".format(minutes, seconds)
}

fun localDateMillis(year: Int, month: Int, day: Int): Long =
    Calendar.getInstance().apply {
        clear()
        set(year, month, day)
    }.timeInMillis

fun localDateTimeMillis(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long =
    Calendar.getInstance().apply {
        clear()
        set(year, month, day, hour, minute, 0)
    }.timeInMillis

fun utcMillisToYearMonthDay(utcMillis: Long): Triple<Int, Int, Int> {
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { timeInMillis = utcMillis }
    return Triple(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
}