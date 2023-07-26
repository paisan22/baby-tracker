package nl.paisan.babytracker.ui.screen.addPhysicals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.paisan.babytracker.domain.enums.PhysicalType
import javax.inject.Inject

@HiltViewModel
class AddPhysicalsViewModel @Inject constructor(): ViewModel() {
    var uiState by mutableStateOf(AddPhysicalsUiState())
        private set

    fun onPhysicalClick(physicalType: PhysicalType) {
        uiState = when(physicalType) {
            PhysicalType.Weight -> {
                uiState.copy(
                    showLengthWizard = false,
                    showWeightWizard = true
                )
            }

            PhysicalType.Length -> {
                uiState.copy(
                    showLengthWizard = true,
                    showWeightWizard = false
                )
            }
        }
    }
}