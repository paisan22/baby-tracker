package nl.paisan.babytracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.paisan.babytracker.data.repositories.BioRepository
import nl.paisan.babytracker.data.repositories.NutritionRepository
import nl.paisan.babytracker.domain.repositories.IBioRepo
import nl.paisan.babytracker.domain.repositories.INutritionRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindBioRepo(impl: BioRepository): IBioRepo

    @Binds
    abstract fun bindNutritionRepo(impl: NutritionRepository): INutritionRepo
}
