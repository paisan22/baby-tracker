package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import nl.paisan.babytracker.data.entities.BreastLog

@Dao
interface BreastLogDao {
    @Insert
    suspend fun insertBreastLog(breastLog: BreastLog): Long

    @Transaction
    @Delete
    suspend fun deleteLog(log: BreastLog)
}