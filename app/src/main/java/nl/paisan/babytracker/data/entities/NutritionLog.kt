package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_log", indices = [Index(value = ["startTime"])])
data class NutritionLog (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val breastLogId: Long? = null,
    val bottleLogId: Long? = null
)