package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.DiaperLog

@Dao
interface DiaperLogDao {
    @Insert
    suspend fun insertDiaperLog(diaperLog: DiaperLog): Long
    @Query("SELECT * FROM diaper_log ORDER BY start DESC")
    fun getAllDiaperLogs(): Flow<List<DiaperLog>>

    @Transaction
    @Delete
    fun deleteLog(log: DiaperLog)
}