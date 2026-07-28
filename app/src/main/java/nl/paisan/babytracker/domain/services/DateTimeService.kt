package nl.paisan.babytracker.domain.services

import android.content.Context
import nl.paisan.babytracker.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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