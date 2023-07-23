package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails

@Dao
interface NutritionLogDao {
    @Insert
    suspend fun insertNutritionLog(nutritionLog: NutritionLog): Long

    @Query("SELECT * FROM nutrition_log ORDER BY startTime DESC")
    fun getAllNutritionLogs(): Flow<List<NutritionLogWithDetails>>

    @Transaction
    @Delete
    suspend fun deleteLog(log: NutritionLog)
}