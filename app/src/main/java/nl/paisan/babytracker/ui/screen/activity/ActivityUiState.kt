package nl.paisan.babytracker.ui.screen.activity

import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide

data class ActivityUiState(
    val isLoading: Boolean = true,
    val showNutritionWizard: Boolean = false,
    val showRestWizard: Boolean = false,
    val showDiapersWizard: Boolean = false,
    val nutritionState: NutritionState = NutritionState(),
    val currentLogs: List<NutritionLogWithDetails> = listOf()
) {
    val lastBreastLog: NutritionLogWithDetails? get() =
        currentLogs.lastOrNull { it.breastLog != null }

    val lastBottleLog: NutritionLogWithDetails? get() =
        currentLogs.lastOrNull() { it.bottleLog != null }
}

data class NutritionState(
    val start: Long? = null,
    val end: Long? = null,
    val breastSide: BreastSide? = null,
    val bottleType: BottleType? = null,
    val milliliters: Int? = null
)