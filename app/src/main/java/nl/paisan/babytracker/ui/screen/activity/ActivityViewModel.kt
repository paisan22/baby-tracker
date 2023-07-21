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
import nl.paisan.babytracker.domain.repositories.IRestRepo
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val nutritionRepo: INutritionRepo,
    private val restRepo: IRestRepo
): ViewModel() {
    var uiState by mutableStateOf(ActivityUiState())
        private set

    init {
        observeData()

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false
            )
        }
    }

    fun onActivityClick(activityType: ActivityType) {
        when(activityType) {
            ActivityType.Nutrition -> startNutritionWizard()
            ActivityType.Rest -> startRestWizard()
            ActivityType.Diapers -> startDiaperWizard()
        }
    }

    fun onCloseActivity() {
        uiState = uiState.copy(
            showNutritionWizard = false,
            showRestWizard = false,
            showDiapersWizard = false,
        )
    }

    fun addNutritionBreastLog(start: Long, end: Long, breastSide: BreastSide) {
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

    fun addNutritionBottleLog(start: Long, end: Long, bottleType: BottleType, milliliters: Int) {
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

    fun addRestLog(start: Long, end: Long) {
        viewModelScope.launch {
            restRepo.addLog(start = start, end = end)
        }
    }

    private fun startNutritionWizard() {
        uiState = uiState.copy(
            showNutritionWizard = true,
            showDiapersWizard = false,
            showRestWizard = false
        )
    }

    private fun startRestWizard() {
        uiState = uiState.copy(
            showNutritionWizard = false,
            showDiapersWizard = false,
            showRestWizard = true
        )
    }

    private fun startDiaperWizard() {
        uiState = uiState.copy(
            showNutritionWizard = false,
            showDiapersWizard = true,
            showRestWizard = false
        )
    }

    private fun observeData() {
        viewModelScope.launch {
            nutritionRepo.getAllLogs.collect { logs ->
                uiState = uiState.copy(
                    nutritionLogs = logs
                )
            }
        }

        viewModelScope.launch {
            restRepo.getAllLogs.collect { logs ->
                uiState = uiState.copy(
                    restLogs = logs
                )
            }
        }
    }
}