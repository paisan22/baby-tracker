package nl.paisan.babytracker.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class NutritionLogWithDetails(
    @Embedded val nutritionLog: NutritionLog,
    @Relation(
        parentColumn = "breastLogId",
        entityColumn = "id"
    )
    val breastLog: BreastLog?,
    @Relation(
        parentColumn = "bottleLogId",
        entityColumn = "id"
    )
    val bottleLog: BottleLog?
)
