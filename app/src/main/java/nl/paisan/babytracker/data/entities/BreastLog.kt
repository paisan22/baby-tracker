package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.paisan.babytracker.domain.enums.BreastSide

@Entity(tableName = "breast_log")
data class BreastLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val breastSide: BreastSide
)
