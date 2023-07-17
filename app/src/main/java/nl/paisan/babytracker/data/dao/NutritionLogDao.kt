package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails

@Dao
interface NutritionLogDao {
    @Insert
    suspend fun insertNutritionLog(nutritionLog: NutritionLog): Long

    @Query("SELECT * FROM nutrition_log")
    fun getAllNutritionLogs(): Flow<List<NutritionLogWithDetails>>
}