package nl.paisan.babytracker.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import nl.paisan.babytracker.data.dao.BioDao
import nl.paisan.babytracker.data.mappers.toDBModel
import nl.paisan.babytracker.data.mappers.toDomain
import nl.paisan.babytracker.domain.entities.Bio
import nl.paisan.babytracker.domain.repositories.IBioRepo
import nl.paisan.babytracker.domain.repositories.Result
import javax.inject.Inject

class BioRepository @Inject constructor(
    private val bioDao: BioDao
): IBioRepo {
    private val TAG = "BioRepository"

    override val getBio: Flow<Bio?>
        get() = bioDao.getFirst()
            .map { it?.toDomain() }


    override suspend fun insertBio(data: Bio): Result<Unit> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                bioDao.insertBio(bio = data.toDBModel())

                Result.Success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "failed to insert bio", e)
                Result.Error("failed to save bio")
            }
        }
    }
}