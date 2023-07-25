package nl.paisan.babytracker.ui.screen.addWeight

data class AddWeightMeasurementUiState(
    val isLoading: Boolean = true,
    val kilogram: Double? = null,
    val showConfirm: Boolean = false,
)
