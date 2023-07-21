package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.paisan.babytracker.domain.enums.BottleType

@Entity(tableName = "bottle_log")
data class BottleLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bottleType: BottleType,
    val millimeters: Int
)
