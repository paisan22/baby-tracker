package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.WeightMeasurement

interface IWeightMeasurementRepo {
    val getAllMeasurements: Flow<List<WeightMeasurement>>
    suspend fun addMeasurement(registerDate: Long, kilogram: Double)
    suspend fun deleteMeasurement(measurement: WeightMeasurement)
}