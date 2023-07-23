package nl.paisan.babytracker.ui.screen.overviewActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.repositories.IDiaperRepo
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import nl.paisan.babytracker.domain.repositories.IRestRepo
import javax.inject.Inject

@HiltViewModel()
class OverviewActivityViewModel @Inject constructor(
    private val nutritionRepo: INutritionRepo,
    private val restRepo: IRestRepo,
    private val diaperRepo: IDiaperRepo
): ViewModel() {
    private val TAG = "OverviewActivityViewModel"
    var uiState by mutableStateOf(OverviewActivityUiState())
        private set

    init { observeData() }

    fun onDeleteNutritionLog(log: NutritionLogWithDetails) {
        viewModelScope.launch {
            nutritionRepo.deleteLog(log = log)
        }
    }

    fun onDeleteRestLog(log: RestLog) {
        viewModelScope.launch {
            restRepo.deleteLog(log = log)
        }
    }

    fun onDeleteDiaperLog(log: DiaperLog) {
        viewModelScope.launch {
            diaperRepo.deleteLog(log = log)
        }
    }

    private fun observeData() {
        viewModelScope.launch {
            nutritionRepo.getAllLogs.collect {
                uiState = uiState.copy(
                    nutritionLogs = it
                )

                updateLoadingState()
            }
        }

        viewModelScope.launch {
            restRepo.getAllLogs.collect {
                uiState = uiState.copy(
                    restLogs = it
                )

                updateLoadingState()
            }
        }

        viewModelScope.launch {
            diaperRepo.getAllLogs.collect {
                uiState = uiState.copy(
                    diaperLogs = it
                )

                updateLoadingState()
            }
        }
    }

    private fun updateLoadingState() {
        if(
            (uiState.diaperLogs != null) &&
            (uiState.nutritionLogs != null) &&
            (uiState.restLogs != null)
        ) {
            uiState = uiState.copy(
                isLoading = false
            )
        }
    }
}