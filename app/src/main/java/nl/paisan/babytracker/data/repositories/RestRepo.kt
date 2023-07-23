package nl.paisan.babytracker.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.RestLogDao
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.repositories.IRestRepo
import javax.inject.Inject

class RestRepo @Inject constructor(
    private val restLogDao: RestLogDao
): IRestRepo {
    private val TAG = "RestRepo"

    override val getAllLogs: Flow<List<RestLog>>
        get() = restLogDao.getAllRestLogs()

    override suspend fun addLog(start: Long, end: Long) {
        withContext(Dispatchers.IO) {
            try {
                restLogDao.insertRestLog(
                    restLogDao = RestLog(
                        start = start,
                        end = end
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "add rest log failed", e)
            }
        }
    }

    override suspend fun deleteLog(log: RestLog) {
        withContext(Dispatchers.IO) {
            try {
                restLogDao.deleteLog(log = log)
            } catch (e: Exception) {
                Log.e(TAG, "delete rest log failed", e)
            }
        }
    }
}