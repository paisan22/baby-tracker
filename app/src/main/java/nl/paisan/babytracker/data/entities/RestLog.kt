package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rest_log", indices = [Index(value = ["start"])])
data class RestLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val start: Long,
    val end: Long,
)
