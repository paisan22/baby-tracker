package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.commands.AddBottleLogCommand
import nl.paisan.babytracker.domain.commands.AddBreastLogCommand

interface INutritionRepo {
    val getAllLogs: Flow<List<NutritionLogWithDetails>>

    suspend fun addBreastLog(command: AddBreastLogCommand)
    suspend fun addBottleLog(command: AddBottleLogCommand)
    suspend fun deleteLog(log: NutritionLogWithDetails)
}

