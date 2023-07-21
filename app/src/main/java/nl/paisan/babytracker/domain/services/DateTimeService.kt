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

fun Context.getDelta(startDate: Long, endDate: Long): String {
    val pattern = getString(R.string.mm_ss)
    val language = getString(R.string.language)

    val delta = endDate - startDate

    val deltaDate = Date(delta)
    val deltaDateFormat =
        SimpleDateFormat(
            pattern,
            Locale(language, language.uppercase())
        )

    return deltaDateFormat.format(deltaDate)
}