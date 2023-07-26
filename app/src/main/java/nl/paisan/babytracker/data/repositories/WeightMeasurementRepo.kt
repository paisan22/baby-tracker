package nl.paisan.babytracker.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.WeightMeasurementDao
import nl.paisan.babytracker.data.entities.WeightMeasurement
import nl.paisan.babytracker.domain.repositories.IWeightMeasurementRepo
import java.lang.Exception
import javax.inject.Inject

class WeightMeasurementRepo @Inject constructor(
    private val weightMeasurementDao: WeightMeasurementDao
): IWeightMeasurementRepo {
    private val TAG = "WeightMeasurementRepo"

    override val getAllMeasurements: Flow<List<WeightMeasurement>>
        get() = weightMeasurementDao.getAllMeasurements()

    override suspend fun addMeasurement(registerDate: Long, kilogram: Double) {
        withContext(Dispatchers.IO) {
            try {
                weightMeasurementDao.insertMeasurement(
                    measurement = WeightMeasurement(
                        registrationDate = registerDate,
                        kilogram = kilogram
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "add weight measurement failed", e)
            }
        }
    }

    override suspend fun deleteMeasurement(measurement: WeightMeasurement) {
        withContext(Dispatchers.IO) {
            try {
                weightMeasurementDao.deleteMeasurement(measurement = measurement)
            } catch (e: Exception) {
                Log.e(TAG, "add weight measurement failed", e)
            }
        }
    }
}