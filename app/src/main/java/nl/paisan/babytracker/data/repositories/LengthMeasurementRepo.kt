package nl.paisan.babytracker.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.LengthMeasurementDao
import nl.paisan.babytracker.data.entities.LengthMeasurement
import nl.paisan.babytracker.domain.repositories.ILengthMeasurementRepo
import javax.inject.Inject

class LengthMeasurementRepo @Inject constructor(
    private val lengthMeasurementDao: LengthMeasurementDao
): ILengthMeasurementRepo {
    private val TAG = "LengthMeasurementRepo"

    override val getAll: Flow<List<LengthMeasurement>>
        get() = lengthMeasurementDao.getAllMeasurements()

    override suspend fun addMeasurement(registrationDate: Long, centimeter: Double) {
        withContext(Dispatchers.IO) {
            try {
                lengthMeasurementDao.insertMeasurement(
                    measurement = LengthMeasurement(
                        registrationDate = registrationDate,
                        centimeter = centimeter
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "add length measurement failed", e)
            }
        }
    }

    override suspend fun deleteMeasurement(measurement: LengthMeasurement) {
        withContext(Dispatchers.IO) {
            try {
                lengthMeasurementDao.deleteMeasurement(measurement = measurement)
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "add length measurement failed", e)
            }
        }
    }
}