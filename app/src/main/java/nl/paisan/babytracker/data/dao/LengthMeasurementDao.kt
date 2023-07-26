package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.LengthMeasurement

@Dao
interface LengthMeasurementDao {
    @Insert
    suspend fun insertMeasurement(measurement: LengthMeasurement)
    @Query("SELECT * FROM length_measurement ORDER BY registrationDate DESC")
    fun getAllMeasurements(): Flow<List<LengthMeasurement>>
    @Transaction
    @Delete
    fun deleteMeasurement(measurement: LengthMeasurement)
}