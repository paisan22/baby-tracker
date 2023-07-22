package nl.paisan.babytracker.ui.screen.overviewActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    var uiState by mutableStateOf(OverviewActivityUiState())
        private set

    init {
        observeData()
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

        val launch = viewModelScope.launch {
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