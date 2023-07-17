package nl.paisan.babytracker.ui.screen.bio

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.Gender
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTdatePicker
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.common.BTsingleSelection
import nl.paisan.babytracker.ui.common.BTtextField
import nl.paisan.babytracker.ui.screen.ScreenWrapper

@Composable
fun BioScreen(
    vm: BioViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // name
            BTtextField(
                onChange = { vm.onNameChange(it) },
                initValue = vm.uiState.bio.name,
                label = stringResource(R.string.label_name),
                placeholder = stringResource(R.string.placeholder_example_name)
            )

            // birthday
            BTdatePicker(
                onDateSelection = { vm.onDateSelection(it) },
                dateToPickName = stringResource(R.string.noun_birthday),
                currentDate = vm.uiState.newBio.birthday
            )

            // gender
            BTsingleSelection(
                title = stringResource(R.string.noun_gender),
                options = Gender.values().toList().map { it.name },
                onSelection = { vm.onGenderSelection(Gender.valueOf(it)) },
                initialSelectedIndex = Gender.values().indexOf(vm.uiState.bio.gender)
            )

            // length
            BTnumberTextField(
                initialValue = vm.uiState.bio.length,
                onChange = { vm.onLengthChange(it) },
                label = stringResource(R.string.label_birth_length_in_centimeters),
                placeholder = stringResource(R.string.placeholder_example_length_53)
            )

            // weight
            BTnumberTextField(
                initialValue = vm.uiState.bio.weight,
                onChange = { vm.onWeightChange(it) },
                label = stringResource(R.string.label_birth_weight_in_grams),
                placeholder = stringResource(R.string.placeholder_example_weight_2500)
            )

            // save button
            BTbutton(
                name = stringResource(R.string.action_save),
                onClick = {
                    vm.onSave()

                    Toast
                        .makeText(context, "Bio updated!", Toast.LENGTH_SHORT)
                        .show()
                          },
                enabled = vm.uiState.hasUpdates
            )
        }
    }
}
