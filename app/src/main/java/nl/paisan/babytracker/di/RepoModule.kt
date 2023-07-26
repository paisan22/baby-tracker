package nl.paisan.babytracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.paisan.babytracker.data.repositories.BioRepo
import nl.paisan.babytracker.data.repositories.DiaperRepo
import nl.paisan.babytracker.data.repositories.LengthMeasurementRepo
import nl.paisan.babytracker.data.repositories.NutritionRepo
import nl.paisan.babytracker.data.repositories.RestRepo
import nl.paisan.babytracker.data.repositories.WeightMeasurementRepo
import nl.paisan.babytracker.domain.repositories.IBioRepo
import nl.paisan.babytracker.domain.repositories.IDiaperRepo
import nl.paisan.babytracker.domain.repositories.ILengthMeasurementRepo
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import nl.paisan.babytracker.domain.repositories.IRestRepo
import nl.paisan.babytracker.domain.repositories.IWeightMeasurementRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindBioRepo(impl: BioRepo): IBioRepo
    @Binds
    abstract fun bindNutritionRepo(impl: NutritionRepo): INutritionRepo
    @Binds
    abstract fun bindRestRepo(impl: RestRepo): IRestRepo
    @Binds
    abstract fun bindDiaperRepo(impl: DiaperRepo): IDiaperRepo
    @Binds
    abstract fun bindWeightMeasurementRepo(impl: WeightMeasurementRepo): IWeightMeasurementRepo
    @Binds
    abstract fun bindLengthMeasurementRepo(impl: LengthMeasurementRepo): ILengthMeasurementRepo
}
