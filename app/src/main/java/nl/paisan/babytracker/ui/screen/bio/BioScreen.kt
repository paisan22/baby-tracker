package nl.paisan.babytracker.ui.screen.bio

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.paisan.babytracker.domain.enums.Gender
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun BioScreen(
    vm: BioViewModel = hiltViewModel()
) {
    if(vm.uiState.isLoading) {
        // TODO: add loading state
        Text(text = "Loading...")
    } else {
        val nameValue = remember { mutableStateOf(TextFieldValue(vm.uiState.bio.name)) }
        Column {
            // name
            // TODO: extract to common
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nameValue.value,
                onValueChange = { newValue ->
                    if(newValue.text != nameValue.value.text) {
                        vm.onNameChange(newValue.text)
                    }

                    nameValue.value = newValue
                },
                label = { Text("Name") },
                placeholder = { Text("Example: Alex...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            // birthday
            // TODO: extract date-picker
            AssistChip(
                modifier = Modifier.fillMaxWidth(),
                onClick = { vm.showDatePicker(true) },
                label = {
                    val text = if(vm.uiState.newBio.birthday == null) {
                        "Add birthday"
                    } else {
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                        val date = Date(vm.uiState.newBio.birthday!!)
                        "Birthday: ${dateFormat.format(date)}"
                    }
                    Text(text)
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "Localized description",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

            if(vm.uiState.showDatePicker) {
                BTDatePickerModal(
                    initialSelectedDate = vm.uiState.newBio.birthday,
                    onDateSelection = { vm.onDateSelection(it) },
                    show = { vm.showDatePicker(it) }
                )
            }

            // gender
            // TODO: extract selection composable
            Text(text = "Gender:")
            val genderOptions = Gender.values().toList()

            var genderCurrentIndex = 0
            vm.uiState.bio.gender.let {
                genderCurrentIndex = if(it == Gender.Girl) 1
                else 0
            }

            val (selectedGenderOption, onGenderOptionSelected) =
                remember { mutableStateOf(genderOptions[genderCurrentIndex]) }

            Column(Modifier.selectableGroup()) {
                genderOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedGenderOption),
                                onClick = {
                                    onGenderOptionSelected(text)
                                    vm.onGenderSelection(gender = text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedGenderOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            // length
            // TODO: extract and rename to DTNumberField
            NumberTextField(
                initialValue = vm.uiState.bio.length,
                onChange = { vm.onLengthChange(it) },
                label = "Birth length in centimeters",
                placeholder = "Example: 53"
            )

            // weight
            // TODO: extract and rename to DTNumberField
            NumberTextField(
                initialValue = vm.uiState.bio.weight,
                onChange = { vm.onWeightChange(it) },
                label = "Birth weight in grams",
                placeholder = "Example: 2500"
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { vm.onSave() },
                enabled = vm.uiState.hasUpdates
            ) { Text("Save") }
        }
    }
}

@Composable
fun NumberTextField(
    initialValue: Int?,
    onChange: (value: Int) -> Unit,
    label: String,
    placeholder: String,
) {

    var number by remember { mutableStateOf(initialValue?.toString() ?: "") }

    OutlinedTextField(
        value = number,
        onValueChange = { newValue ->
            number = newValue
                .replace(",","")
                .replace(".", "")

            if(number.isNotEmpty()) {
                onChange(number.toInt())
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label)},
        modifier = Modifier.fillMaxWidth()
    )
}

// TODO: move to common package
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTDatePickerModal(
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
