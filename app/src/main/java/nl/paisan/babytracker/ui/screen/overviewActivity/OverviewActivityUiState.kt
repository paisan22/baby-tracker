package nl.paisan.babytracker.ui.screen.overviewActivity

import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.data.entities.RestLog

data class OverviewActivityUiState(
    val isLoading: Boolean = true,
    val nutritionLogs: List<NutritionLogWithDetails>? = null,
    val restLogs: List<RestLog>? = null,
    val diaperLogs: List<DiaperLog>? = null,
)
