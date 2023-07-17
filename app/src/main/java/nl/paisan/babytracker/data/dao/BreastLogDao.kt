package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import nl.paisan.babytracker.data.entities.BreastLog

@Dao
interface BreastLogDao {
    @Insert
    suspend fun insertBreastLog(breastLog: BreastLog): Long
}