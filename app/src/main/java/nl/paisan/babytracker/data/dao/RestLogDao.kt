package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.RestLog

@Dao
interface RestLogDao {
    @Insert
    suspend fun insertRestLog(restLogDao: RestLog): Long

    @Query("SELECT * FROM rest_log ORDER BY start DESC")
    fun getAllRestLogs(): Flow<List<RestLog>>

    @Transaction
    @Delete
    fun deleteLog(log: RestLog)
}