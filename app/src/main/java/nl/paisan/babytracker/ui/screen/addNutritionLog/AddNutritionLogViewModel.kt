package nl.paisan.babytracker.ui.screen.addNutritionLog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.domain.commands.AddBottleLogCommand
import nl.paisan.babytracker.domain.commands.AddBreastLogCommand
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import nl.paisan.babytracker.domain.services.localDateTimeMillis
import javax.inject.Inject

@HiltViewModel
class AddNutritionLogViewModel @Inject constructor(
    private val nutritionRepo: INutritionRepo
) : ViewModel() {
    var uiState by mutableStateOf(AddNutritionLogUiState())
        private set

    fun onNutritionTypeSelected(type: NutritionType) {
        uiState = uiState.copy(nutritionType = type)
    }

    fun onBreastSideSelected(side: BreastSide) {
        uiState = uiState.copy(breastSide = side)
    }

    fun onBottleTypeSelected(type: BottleType) {
        uiState = uiState.copy(bottleType = type)
    }

    fun onMillilitersChanged(value: Int) {
        uiState = uiState.copy(milliliters = value)
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        uiState = uiState.copy(year = year, month = month, day = day)
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        uiState = uiState.copy(hour = hour, minute = minute)
    }

    fun onMinutesChanged(value: Int) {
        uiState = uiState.copy(minutes = value)
    }

    fun onSecondsChanged(value: Int) {
        uiState = uiState.copy(seconds = value)
    }

    fun onAdd() {
        val start = localDateTimeMillis(
            year = uiState.year,
            month = uiState.month,
            day = uiState.day,
            hour = uiState.hour,
            minute = uiState.minute
        )
        val end = start + (uiState.minutes ?: 0) * 60_000L + (uiState.seconds ?: 0) * 1000L

        viewModelScope.launch {
            when (uiState.nutritionType) {
                NutritionType.Breast -> nutritionRepo.addBreastLog(
                    AddBreastLogCommand(
                        start = start,
                        end = end,
                        breastSide = uiState.breastSide ?: return@launch
                    )
                )
                NutritionType.Bottle -> nutritionRepo.addBottleLog(
                    AddBottleLogCommand(
                        start = start,
                        end = end,
                        bottleType = uiState.bottleType ?: return@launch,
                        millimeters = uiState.milliliters ?: return@launch
                    )
                )
                null -> return@launch
            }
        }
    }
}
