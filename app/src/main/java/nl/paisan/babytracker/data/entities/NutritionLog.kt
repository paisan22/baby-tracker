package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_log")
data class NutritionLog (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val breastLogId: Long? = null,
    val bottleLogId: Long? = null
)