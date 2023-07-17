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
import nl.paisan.babytracker.data.migrations.MigrationFrom1To2
import nl.paisan.babytracker.data.migrations.MigrationFrom2To3
import nl.paisan.babytracker.data.migrations.MigrationFrom3To4

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideBioDao(database: BabyTrackerDB): BioDao {
        return database.bioDao()
    }
    @Provides
    fun provideNuttritionLogDao(database: BabyTrackerDB): NutritionLogDao {
        return database.nuttrionLogDao()
    }
    @Provides
    fun provideBreastLogDao(database: BabyTrackerDB): BreastLogDao {
        return database.breastLoogDao()
    }
    @Provides
    fun provideBottleLogDao(database: BabyTrackerDB): BottleLogDao {
        return database.bottleLogDao()
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BabyTrackerDB {
        val DATABASE_NAME = "baby_tracker_db"

        return Room.databaseBuilder(
            context,
            BabyTrackerDB::class.java,
            DATABASE_NAME,
        )
            .addMigrations(
                MigrationFrom1To2(),
                MigrationFrom2To3(),
                MigrationFrom3To4()
            )
            .build()
    }
}