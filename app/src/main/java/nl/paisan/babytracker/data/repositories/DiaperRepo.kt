package nl.paisan.babytracker.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.DiaperLogDao
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.domain.enums.DiaperType
import nl.paisan.babytracker.domain.repositories.IDiaperRepo
import javax.inject.Inject
import kotlin.Exception

class DiaperRepo @Inject constructor(
    private val diaperLogDao: DiaperLogDao
): IDiaperRepo {
    private val TAG = "DiaperRepo"

    override val getAllLogs: Flow<List<DiaperLog>>
        get() = diaperLogDao.getAllDiaperLogs()

    override suspend fun addDiaperLog(start: Long, type: DiaperType, note: String?) {

        withContext(Dispatchers.IO) {
            try {
                diaperLogDao.insertDiaperLog(
                    diaperLog = DiaperLog(
                        start = start,
                        type = type,
                        note = note
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "add diaper log failed", e)
            }
        }
    }

    override suspend fun deleteLog(log: DiaperLog) {
        withContext(Dispatchers.IO) {
            try {
                diaperLogDao.deleteLog(log = log)
            } catch (e: Exception) {
                Log.e(TAG, "delete diaper log failed", e)
            }
        }
    }


}