package nl.paisan.babytracker.ui.screen.addWeight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.domain.repositories.IWeightMeasurementRepo
import javax.inject.Inject

@HiltViewModel
class AddWeightMeasurementViewModel @Inject constructor(
    private val measurementRepo: IWeightMeasurementRepo
): ViewModel() {
    var uiState by mutableStateOf(AddWeightMeasurementUiState())
        private set


    fun onValueChanged(newValue: Double?) {
        uiState = uiState.copy(
            kilogram = newValue
        )
    }

    fun showConfirm(show: Boolean) {
        uiState = uiState.copy(
            showConfirm = show
        )
    }

    fun onSave() {
        viewModelScope.launch {
            measurementRepo.addMeasurement(
                registerDate = System.currentTimeMillis(),
                kilogram = uiState.kilogram?: 0.0
            )

            onValueChanged(null)
        }
    }
}