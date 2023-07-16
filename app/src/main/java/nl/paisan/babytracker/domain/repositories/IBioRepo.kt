package nl.paisan.babytracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.domain.entities.Bio

interface IBioRepo {
    val getBio: Flow<Bio?>
    suspend fun insertBio(data: Bio): Result<Unit>
}