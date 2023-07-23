package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import nl.paisan.babytracker.data.entities.BottleLog

@Dao
interface BottleLogDao {
    @Insert
    suspend fun insertBottleLog(bottleLog: BottleLog): Long

    @Transaction
    @Delete
    suspend fun deleteLog(log: BottleLog)
}