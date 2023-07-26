package nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewLengthMeasurement

import nl.paisan.babytracker.data.entities.LengthMeasurement

data class OverviewLengthMeasurementUiState (
    val isLoading: Boolean = false,
    val measurements: List<LengthMeasurement> = listOf()
)
