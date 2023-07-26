package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.LengthMeasurement

interface ILengthMeasurementRepo {
    val getAll: Flow<List<LengthMeasurement>>
    suspend fun addMeasurement(registrationDate: Long, centimeter: Double)
    suspend fun deleteMeasurement(measurement: LengthMeasurement)
}