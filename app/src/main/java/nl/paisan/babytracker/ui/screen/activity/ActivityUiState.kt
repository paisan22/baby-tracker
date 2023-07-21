package nl.paisan.babytracker.ui.screen.activity

import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide

data class ActivityUiState(
    val isLoading: Boolean = true,
    val showNutritionWizard: Boolean = false,
    val showRestWizard: Boolean = false,
    val showDiapersWizard: Boolean = false,
    val nutritionState: NutritionState = NutritionState(),
    val nutritionLogs: List<NutritionLogWithDetails> = listOf(),
    val restLogs: List<RestLog> = listOf(),
) {
    val lastBreastLog: NutritionLogWithDetails? get() =
        nutritionLogs.lastOrNull { it.breastLog != null }

    val lastBottleLog: NutritionLogWithDetails? get() =
        nutritionLogs.lastOrNull() { it.bottleLog != null }

    val lastRestLog: RestLog? get() =
        restLogs.lastOrNull()
}

data class NutritionState(
    val start: Long? = null,
    val end: Long? = null,
    val breastSide: BreastSide? = null,
    val bottleType: BottleType? = null,
    val milliliters: Int? = null
)