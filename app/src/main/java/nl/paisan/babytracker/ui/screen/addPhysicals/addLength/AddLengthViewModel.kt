package nl.paisan.babytracker.ui.screen.addPhysicals.addLength

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.domain.repositories.ILengthMeasurementRepo
import javax.inject.Inject

@HiltViewModel
class AddLengthViewModel @Inject constructor(
    private val lengthMeasurementRepo: ILengthMeasurementRepo
): ViewModel() {
    var uiState by mutableStateOf(AddLengthUiState())
        private set

    fun showConfirm(show: Boolean) {
        uiState = uiState.copy(
            showConfirmDialog = show
        )
    }

    fun onSave() {
        viewModelScope.launch {
            lengthMeasurementRepo.addMeasurement(
                registrationDate = System.currentTimeMillis(),
                centimeter = uiState.centimeters?: 0.0
            )
        }
    }

    fun onValueChanged(value: Double?) {
        uiState = uiState.copy(
            centimeters = value
        )
    }
}
