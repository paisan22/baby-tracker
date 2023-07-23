package nl.paisan.babytracker.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.BottleLogDao
import nl.paisan.babytracker.data.dao.BreastLogDao
import nl.paisan.babytracker.data.dao.NutritionLogDao
import nl.paisan.babytracker.data.entities.BottleLog
import nl.paisan.babytracker.data.entities.BreastLog
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.commands.AddBottleLogCommand
import nl.paisan.babytracker.domain.commands.AddBreastLogCommand
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import javax.inject.Inject

class NutritionRepo @Inject constructor(
    private val nutritionLogDao: NutritionLogDao,
    private val breastLogDao: BreastLogDao,
    private val bottleLogDao: BottleLogDao
) : INutritionRepo {
    private val TAG = "NutritionRepo"

    override val getAllLogs: Flow<List<NutritionLogWithDetails>>
        get() = nutritionLogDao.getAllNutritionLogs()

    override suspend fun addBreastLog(command: AddBreastLogCommand) {
        withContext(Dispatchers.IO) {
            try {
                val breastLogId = breastLogDao.insertBreastLog(
                    breastLog = BreastLog(
                        breastSide = command.breastSide
                    )
                )

                nutritionLogDao.insertNutritionLog(
                    nutritionLog = NutritionLog(
                    startTime = command.start,
                    endTime = command.end,
                    breastLogId = breastLogId
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "add breast log failed", e)
            }
        }
    }

    override suspend fun addBottleLog(command: AddBottleLogCommand) {
        withContext(Dispatchers.IO) {
            try {
                val bottleLogId = bottleLogDao.insertBottleLog(
                    bottleLog = BottleLog(
                        bottleType = command.bottleType,
                        millimeters = command.millimeters
                    )
                )

                nutritionLogDao.insertNutritionLog(
                    nutritionLog = NutritionLog(
                        startTime = command.start,
                        endTime = command.end,
                        bottleLogId = bottleLogId
                    )
                )
            } catch(e: Exception) {
                Log.e(TAG, "add bottle log failed", e)
            }
        }
    }

    override suspend fun deleteLog(log: NutritionLogWithDetails) {
        withContext(Dispatchers.IO) {
            try {
                nutritionLogDao.deleteLog(log = log.nutritionLog)
                log.bottleLog?.let { bottleLogDao.deleteLog(it) }
                log.breastLog?.let { breastLogDao.deleteLog(it) }

            } catch (e: Exception) {
                Log.e(TAG, "delete nutrition log failed", e)
            }
        }
    }
}