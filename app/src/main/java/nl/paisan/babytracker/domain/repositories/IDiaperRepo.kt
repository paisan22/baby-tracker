package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.domain.enums.DiaperType

interface IDiaperRepo {
    val getAllLogs: Flow<List<DiaperLog>>
    suspend fun addDiaperLog(
        start: Long,
        type: DiaperType,
        note: String? = null
    )
}