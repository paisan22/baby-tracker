package nl.paisan.babytracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.paisan.babytracker.data.dao.BioDao
import nl.paisan.babytracker.data.entities.Bio
import nl.paisan.babytracker.data.mappers.GenderConverter

@Database(entities = [Bio::class], version = 2)
@TypeConverters(GenderConverter::class)
abstract class BabyTrackerDB : RoomDatabase() {
    abstract fun bioDao(): BioDao
}