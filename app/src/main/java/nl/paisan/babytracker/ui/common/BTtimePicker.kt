package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BTtimePicker(
    timeToPickName: String = "",
    currentHour: Int? = null,
    currentMinute: Int? = null,
    onTimeSelection: (hour: Int, minute: Int) -> Unit
) {
    val showTimePicker = remember { mutableStateOf(false) }

    AssistChip(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        onClick = { showTimePicker.value = true },
        label = {
            val text = if (currentHour == null || currentMinute == null) {
                "Pick $timeToPickName"
            } else {
                "$timeToPickName: ${"%02d".format(currentHour)}:${"%02d".format(currentMinute)}"
            }
            Text(text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "Localized description",
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )

    if (showTimePicker.value) {
        TimePickerModal(
            initialHour = currentHour ?: 0,
            initialMinute = currentMinute ?: 0,
            onTimeSelection = onTimeSelection,
            show = { showTimePicker.value = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerModal(
    initialHour: Int,
    initialMinute: Int,
    show: (show: Boolean) -> Unit,
    onTimeSelection: (hour: Int, minute: Int) -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = { show(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    show(false)
                    onTimeSelection(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { show(false) }) {
                Text("Cancel")
            }
        },
        text = { TimePicker(state = timePickerState) }
    )
}
