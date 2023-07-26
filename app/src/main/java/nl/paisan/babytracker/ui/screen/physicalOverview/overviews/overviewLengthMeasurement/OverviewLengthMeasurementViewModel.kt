package nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewLengthMeasurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.data.entities.LengthMeasurement
import nl.paisan.babytracker.domain.repositories.ILengthMeasurementRepo
import javax.inject.Inject

@HiltViewModel
class OverviewLengthMeasurementViewModel @Inject constructor(
    private val lengthMeasurementRepo: ILengthMeasurementRepo
): ViewModel() {
    var uisState by mutableStateOf(OverviewLengthMeasurementUiState())
        private set

    init { observeData() }

    fun onDelete(measurement: LengthMeasurement) {
        viewModelScope.launch {
            lengthMeasurementRepo.deleteMeasurement(measurement = measurement)
        }
    }

    private fun observeData() {
        viewModelScope.launch {
            lengthMeasurementRepo.getAll.collect { measurements ->
                uisState = uisState.copy(
                    measurements = measurements,
                    isLoading = false,
                )
            }
        }
    }
}
