package nl.paisan.babytracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.paisan.babytracker.data.dao.BioDao
import nl.paisan.babytracker.data.dao.BottleLogDao
import nl.paisan.babytracker.data.dao.BreastLogDao
import nl.paisan.babytracker.data.dao.DiaperLogDao
import nl.paisan.babytracker.data.dao.NutritionLogDao
import nl.paisan.babytracker.data.dao.RestLogDao
import nl.paisan.babytracker.data.entities.Bio
import nl.paisan.babytracker.data.entities.BottleLog
import nl.paisan.babytracker.data.entities.BreastLog
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.data.mappers.GenderConverter

@Database(
    entities =
    [
        Bio::class, NutritionLog::class,
        BreastLog::class,
        BottleLog::class,
        RestLog::class,
        DiaperLog::class,
    ]
    , version = 4)
@TypeConverters(GenderConverter::class)
abstract class BabyTrackerDB : RoomDatabase() {
    abstract fun bioDao(): BioDao
    abstract fun nuttrionLogDao(): NutritionLogDao
    abstract fun breastLoogDao(): BreastLogDao
    abstract fun bottleLogDao(): BottleLogDao
    abstract fun restLogDao(): RestLogDao
    abstract fun diaperLogDao(): DiaperLogDao
}