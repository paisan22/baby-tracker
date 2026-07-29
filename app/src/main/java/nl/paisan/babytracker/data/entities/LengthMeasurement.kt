package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("length_measurement", indices = [Index(value = ["registrationDate"])])
data class LengthMeasurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val registrationDate: Long,
    val centimeter: Double,
)
