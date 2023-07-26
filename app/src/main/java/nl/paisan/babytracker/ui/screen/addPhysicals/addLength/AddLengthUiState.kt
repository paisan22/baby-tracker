package nl.paisan.babytracker.ui.screen.addPhysicals.addLength

data class AddLengthUiState (
    val isLoading: Boolean = false,
    val showConfirmDialog: Boolean = false,
    val centimeters: Double? = null,
)
