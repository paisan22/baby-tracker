package nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewWeightMeasurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.data.entities.WeightMeasurement
import nl.paisan.babytracker.domain.repositories.IWeightMeasurementRepo
import javax.inject.Inject

@HiltViewModel
class OverviewWeightMeasurementViewModel @Inject constructor(
    private val weightMeasurementRepo: IWeightMeasurementRepo
): ViewModel() {
    var uisState by mutableStateOf(OverviewWeightMeasurementUiState())
        private set

    init { observeData() }

    fun onDelete(measurement: WeightMeasurement) {
        viewModelScope.launch {
            weightMeasurementRepo.deleteMeasurement(measurement = measurement)
        }
    }

    private fun observeData() {
        viewModelScope.launch {
            weightMeasurementRepo.getAllMeasurements.collect { measurements ->
                uisState = uisState.copy(
                    measurements = measurements,
                    isLoading = false,
                )
            }
        }
    }
}
