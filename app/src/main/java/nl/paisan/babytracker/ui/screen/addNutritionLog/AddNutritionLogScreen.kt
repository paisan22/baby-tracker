package nl.paisan.babytracker.ui.screen.addNutritionLog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import nl.paisan.babytracker.domain.services.localDateMillis
import nl.paisan.babytracker.domain.services.utcMillisToYearMonthDay
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTdatePicker
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.common.BTsingleSelection
import nl.paisan.babytracker.ui.common.BTtimePicker
import nl.paisan.babytracker.ui.screen.ScreenWrapper

@Composable
fun AddNutritionLogScreen(
    navHostController: NavHostController,
    vm: AddNutritionLogViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(enabled = true, state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val breastLabel = stringResource(R.string.noun_breast)
            val bottleLabel = stringResource(R.string.noun_bottle)
            val leftBreastLabel = stringResource(R.string.noun_left_breast)
            val rightBreastLabel = stringResource(R.string.noun_right_breast)
            val formulaLabel = stringResource(R.string.noun_formula)
            val breastMilkLabel = stringResource(R.string.noun_breast_milk)

            BTsingleSelection(
                title = stringResource(R.string.sentence_breast_or_bottle),
                options = listOf(breastLabel, bottleLabel),
                onSelection = { selected ->
                    vm.onNutritionTypeSelected(
                        if (selected == breastLabel) NutritionType.Breast else NutritionType.Bottle
                    )
                }
            )

            when (vm.uiState.nutritionType) {
                NutritionType.Breast -> {
                    BTsingleSelection(
                        title = stringResource(R.string.sentence_left_or_right_breast),
                        options = listOf(leftBreastLabel, rightBreastLabel),
                        onSelection = { selected ->
                            vm.onBreastSideSelected(
                                if (selected == leftBreastLabel) BreastSide.Left else BreastSide.Right
                            )
                        }
                    )
                }
                NutritionType.Bottle -> {
                    BTsingleSelection(
                        title = stringResource(R.string.sentence_formula_or_breast_milk),
                        options = listOf(formulaLabel, breastMilkLabel),
                        onSelection = { selected ->
                            vm.onBottleTypeSelected(
                                if (selected == formulaLabel) BottleType.Formula else BottleType.BreastMilk
                            )
                        }
                    )

                    BTnumberTextField(
                        initialValue = vm.uiState.milliliters,
                        onChange = { vm.onMillilitersChanged(it) },
                        label = stringResource(R.string.noun_milliliters),
                        placeholder = stringResource(R.string.label_example_150)
                    )
                }
                null -> {}
            }

            BTdatePicker(
                dateToPickName = stringResource(R.string.noun_date),
                currentDate = localDateMillis(
                    year = vm.uiState.year,
                    month = vm.uiState.month,
                    day = vm.uiState.day
                ),
                onDateSelection = { utcMillis ->
                    val (year, month, day) = utcMillisToYearMonthDay(utcMillis)
                    vm.onDateSelected(year = year, month = month, day = day)
                }
            )

            BTtimePicker(
                timeToPickName = stringResource(R.string.noun_time),
                currentHour = vm.uiState.hour,
                currentMinute = vm.uiState.minute,
                onTimeSelection = { hour, minute -> vm.onTimeSelected(hour = hour, minute = minute) }
            )

            BTnumberTextField(
                initialValue = vm.uiState.minutes,
                onChange = { vm.onMinutesChanged(it) },
                label = stringResource(R.string.noun_minutes),
                placeholder = stringResource(R.string.label_example_5)
            )

            BTnumberTextField(
                initialValue = vm.uiState.seconds,
                onChange = { vm.onSecondsChanged(it) },
                label = stringResource(R.string.noun_seconds),
                placeholder = stringResource(R.string.label_example_30)
            )

            BTbutton(
                name = stringResource(R.string.action_add),
                enabled = vm.uiState.canAdd,
                onClick = {
                    vm.onAdd()
                    navHostController.popBackStack()
                }
            )
        }
    }
}
