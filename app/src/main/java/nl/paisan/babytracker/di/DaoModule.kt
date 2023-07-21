package nl.paisan.babytracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.paisan.babytracker.data.BabyTrackerDB
import nl.paisan.babytracker.data.dao.BioDao
import nl.paisan.babytracker.data.dao.BottleLogDao
import nl.paisan.babytracker.data.dao.BreastLogDao
import nl.paisan.babytracker.data.dao.NutritionLogDao
import nl.paisan.babytracker.data.dao.RestLogDao
import nl.paisan.babytracker.data.migrations.MigrationFrom1To2

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideBioDao(database: BabyTrackerDB): BioDao = database.bioDao()
    @Provides
    fun provideNuttritionLogDao(database: BabyTrackerDB): NutritionLogDao = database.nuttrionLogDao()
    @Provides
    fun provideBreastLogDao(database: BabyTrackerDB): BreastLogDao = database.breastLoogDao()
    @Provides
    fun provideBottleLogDao(database: BabyTrackerDB): BottleLogDao = database.bottleLogDao()
    @Provides
    fun provideRestLogDao(database: BabyTrackerDB): RestLogDao = database.restLogDao()
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BabyTrackerDB {
        val DATABASE_NAME = "baby_tracker_db"

        return Room.databaseBuilder(
            context,
            BabyTrackerDB::class.java,
            DATABASE_NAME,
        )
//            .addMigrations(
//                MigrationFrom1To2(),
//            )
            .build()
    }
}