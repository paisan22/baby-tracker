package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.WeightMeasurement

@Dao
interface WeightMeasurementDao {
    @Insert
    suspend fun insertMeasurement(measurement: WeightMeasurement): Long

    @Query("SELECT * FROM weight_measurement ORDER BY registrationDate DESC")
    fun getAllMeasurements(): Flow<List<WeightMeasurement>>

    @Transaction
    @Delete
    fun deleteMeasurement(measurement: WeightMeasurement)
}