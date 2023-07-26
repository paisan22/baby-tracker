package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_measurement")
data class WeightMeasurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val registrationDate: Long,
    val kilogram: Double,
)
