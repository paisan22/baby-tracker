package nl.paisan.babytracker.ui.screen.addNutritionLog

import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import java.util.Calendar

enum class NutritionType { Breast, Bottle }

data class AddNutritionLogUiState(
    val isLoading: Boolean = false,
    val nutritionType: NutritionType? = null,
    val breastSide: BreastSide? = null,
    val bottleType: BottleType? = null,
    val milliliters: Int? = null,
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH),
    val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    val hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    val minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    val minutes: Int? = 0,
    val seconds: Int? = 0,
) {
    val canAdd: Boolean
        get() = when (nutritionType) {
            NutritionType.Breast -> breastSide != null
            NutritionType.Bottle -> bottleType != null && milliliters != null
            null -> false
        }
}
