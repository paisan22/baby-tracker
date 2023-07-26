package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("length_measurement")
data class LengthMeasurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val registrationDate: Long,
    val centimeter: Double,
)
