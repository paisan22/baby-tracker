package nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewWeightMeasurement

import nl.paisan.babytracker.data.entities.WeightMeasurement

data class OverviewWeightMeasurementUiState (
    val isLoading: Boolean = false,
    val measurements: List<WeightMeasurement> = listOf()
)
