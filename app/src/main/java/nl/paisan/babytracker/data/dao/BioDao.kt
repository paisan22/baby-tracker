package nl.paisan.babytracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.paisan.babytracker.data.entities.Bio

@Dao
interface BioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBio(bio: Bio)

    @Update
    suspend fun update(bio: Bio)

    @Query("SELECT * FROM bio LIMIT 1")
    fun getFirst(): Flow<Bio?>
}