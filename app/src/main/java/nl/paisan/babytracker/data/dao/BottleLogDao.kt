package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import nl.paisan.babytracker.data.entities.BottleLog

@Dao
interface BottleLogDao {
    @Insert
    suspend fun insertBottleLog(bottleLog: BottleLog): Long
}