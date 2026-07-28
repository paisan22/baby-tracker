package nl.paisan.babytracker.ui.screen.addActivity

import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide

data class AddActivityUiState(
    val isLoading: Boolean = true,
    val showNutritionWizard: Boolean = false,
    val showRestWizard: Boolean = false,
    val showDiapersWizard: Boolean = false,
    val nutritionState: NutritionState = NutritionState(),
    val nutritionLogs: List<NutritionLogWithDetails> = listOf(),
    val restLogs: List<RestLog> = listOf(),
    val diaperLogs: List<DiaperLog> = listOf()
) {
    // nutritionLogs/restLogs/diaperLogs are already ordered newest-first (DAO queries are ORDER BY ... DESC),
    // so the most recent entry is the first element, not the last.
    val lastBreastLog: NutritionLogWithDetails? get() =
        nutritionLogs.firstOrNull { it.breastLog != null }

    val lastBottleLog: NutritionLogWithDetails? get() =
        nutritionLogs.firstOrNull { it.bottleLog != null }

    val lastRestLog: RestLog? get() =
        restLogs.firstOrNull()

    val lastDiaperLog: DiaperLog? get() =
        diaperLogs.firstOrNull()
}

data class NutritionState(
    val start: Long? = null,
    val end: Long? = null,
    val breastSide: BreastSide? = null,
    val bottleType: BottleType? = null,
    val milliliters: Int? = null
)