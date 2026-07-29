package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import nl.paisan.babytracker.domain.enums.DiaperType

@Entity(tableName = "diaper_log", indices = [Index(value = ["start"])])
data class DiaperLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val start: Long,
    val type: DiaperType,
    val note: String? = null
)
