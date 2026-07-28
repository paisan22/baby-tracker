package nl.paisan.babytracker.ui.screen.overviewActivity.overviews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.services.millisToMinutesSeconds
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTdatePicker
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.common.BTtimePicker
import nl.paisan.babytracker.ui.common.BTwizardDialog
import java.util.Calendar
import java.util.TimeZone

@Composable
fun EditNutritionLogDialog(
    log: NutritionLogWithDetails,
    onUpdate: (start: Long, end: Long) -> Unit,
    onClose: () -> Unit
) {
    val startCalendar = remember(log) {
        Calendar.getInstance().apply { timeInMillis = log.nutritionLog.startTime }
    }
    val (initialMinutes, initialSeconds) = remember(log) {
        millisToMinutesSeconds(log.nutritionLog.endTime - log.nutritionLog.startTime)
    }

    var year by remember { mutableStateOf(startCalendar.get(Calendar.YEAR)) }
    var month by remember { mutableStateOf(startCalendar.get(Calendar.MONTH)) }
    var day by remember { mutableStateOf(startCalendar.get(Calendar.DAY_OF_MONTH)) }
    var hour by remember { mutableStateOf(startCalendar.get(Calendar.HOUR_OF_DAY)) }
    var minute by remember { mutableStateOf(startCalendar.get(Calendar.MINUTE)) }

    var minutes by remember { mutableStateOf(initialMinutes) }
    var seconds by remember { mutableStateOf(initialSeconds) }

    val displayDate = remember(year, month, day) {
        Calendar.getInstance().apply {
            clear()
            set(year, month, day)
        }.timeInMillis
    }

    BTwizardDialog(
        title = stringResource(R.string.label_edit_nutrition_log),
        onClose = onClose
    ) {
        BTdatePicker(
            dateToPickName = stringResource(R.string.noun_date),
            currentDate = displayDate,
            onDateSelection = { utcMillis ->
                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    timeInMillis = utcMillis
                }
                year = cal.get(Calendar.YEAR)
                month = cal.get(Calendar.MONTH)
                day = cal.get(Calendar.DAY_OF_MONTH)
            }
        )

        BTtimePicker(
            timeToPickName = stringResource(R.string.noun_time),
            currentHour = hour,
            currentMinute = minute,
            onTimeSelection = { newHour, newMinute ->
                hour = newHour
                minute = newMinute
            }
        )

        BTnumberTextField(
            initialValue = minutes,
            onChange = { minutes = it },
            label = stringResource(R.string.noun_minutes),
            placeholder = stringResource(R.string.label_example_5)
        )

        BTnumberTextField(
            initialValue = seconds,
            onChange = { seconds = it },
            label = stringResource(R.string.noun_seconds),
            placeholder = stringResource(R.string.label_example_30)
        )

        BTbutton(
            name = stringResource(R.string.action_update),
            onClick = {
                val newStart = Calendar.getInstance().apply {
                    clear()
                    set(year, month, day, hour, minute, 0)
                }.timeInMillis
                val newEnd = newStart + minutes * 60_000L + seconds * 1000L

                onUpdate(newStart, newEnd)
                onClose()
            }
        )
    }
}
