package nl.paisan.babytracker.ui.screen.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.paisan.babytracker.domain.commands.AddBottleLogCommand
import nl.paisan.babytracker.domain.commands.AddBreastLogCommand
import nl.paisan.babytracker.domain.enums.ActivityType
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val nutritionRepo: INutritionRepo
): ViewModel() {
    var uiState by mutableStateOf(ActivityUiState())
        private set

    init {

        viewModelScope.launch {
            nutritionRepo.getAllLogs.collect { logs ->
                uiState = uiState.copy(
                    currentLogs = logs
                )
            }
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false
            )
        }
    }

    fun onActivityClick(activityType: ActivityType) {
        when(activityType) {
            ActivityType.Nutrition -> startNutritionWizard()
            ActivityType.Rest -> TODO()
            ActivityType.Diapers -> TODO()
        }
    }

    fun onCloseActivity(activityType: ActivityType) {
        when(activityType) {
            ActivityType.Nutrition -> {
                uiState = uiState.copy(
                    showNutritionWizard = false
                )
            }
            ActivityType.Rest -> TODO()
            ActivityType.Diapers -> TODO()
        }
    }

    fun addNutritionBreastLog(
        start: Long,
        end: Long,
        breastSide: BreastSide
    ) {
        viewModelScope.launch {
            nutritionRepo.addBreastLog(
                command = AddBreastLogCommand(
                    start = start,
                    end = end,
                    breastSide = breastSide
                )
            )
        }
    }

    fun addNutritionBottleLog(
        start: Long,
        end: Long,
        bottleType: BottleType,
        milliliters: Int
    ) {
        viewModelScope.launch {
            nutritionRepo.addBottleLog(
                command = AddBottleLogCommand(
                    start = start,
                    end = end,
                    bottleType = bottleType,
                    millimeters = milliliters
                )
            )
        }
    }

    private fun startNutritionWizard() {
        uiState = uiState.copy(
            showNutritionWizard = true
        )
    }
}