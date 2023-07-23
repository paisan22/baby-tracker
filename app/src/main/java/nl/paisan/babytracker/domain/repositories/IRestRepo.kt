package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.RestLog

interface IRestRepo {
    val getAllLogs: Flow<List<RestLog>>
    suspend fun addLog(start: Long, end: Long)
    suspend fun deleteLog(log: RestLog)
}