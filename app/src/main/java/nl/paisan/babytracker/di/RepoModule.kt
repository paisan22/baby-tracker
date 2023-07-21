package nl.paisan.babytracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.paisan.babytracker.data.repositories.BioRepo
import nl.paisan.babytracker.data.repositories.DiaperRepo
import nl.paisan.babytracker.data.repositories.NutritionRepo
import nl.paisan.babytracker.data.repositories.RestRepo
import nl.paisan.babytracker.domain.repositories.IBioRepo
import nl.paisan.babytracker.domain.repositories.IDiaperRepo
import nl.paisan.babytracker.domain.repositories.INutritionRepo
import nl.paisan.babytracker.domain.repositories.IRestRepo

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
    abstract fun bindDiaperRep(impl: DiaperRepo): IDiaperRepo
}
