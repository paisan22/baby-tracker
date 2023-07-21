package nl.paisan.babytracker.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@Composable
fun BTdatePicker(
    dateToPickName: String = "",
    currentDate: Long? = null,
    formatPattern: String = "dd-MM-yyyy",
    onDateSelection: (value: Long) -> Unit
) {
    val showDatePicker = remember { mutableStateOf(false) }

    AssistChip(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        onClick = { showDatePicker.value = true },
        label = {
            val text = if(currentDate == null) {
                stringResource(R.string.action_add) + " " + dateToPickName.lowercase(Locale.ROOT)
            } else {
                val dateFormat = SimpleDateFormat(formatPattern)
                val date = Date(currentDate)
                "$dateToPickName: ${dateFormat.format(date)}"
            }
            Text(text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Localized description",
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )

    if(showDatePicker.value) {
        DatePickerModal(
            initialSelectedDate = currentDate,
            onDateSelection = { onDateSelection(it) },
            show = { showDatePicker.value = it }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerModal(
    initialSelectedDate: Long? = null,
    show: (show: Boolean) -> Unit,
    onDateSelection: (value: Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDate)
    val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

    DatePickerDialog(
        onDismissRequest = { show(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    show(false)
                    datePickerState.selectedDateMillis?.let { date ->
                        onDateSelection(date)
                    }
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { show(false) }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}