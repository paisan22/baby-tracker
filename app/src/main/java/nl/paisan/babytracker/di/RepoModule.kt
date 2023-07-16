package nl.paisan.babytracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.paisan.babytracker.data.BioRepository
import nl.paisan.babytracker.domain.repositories.IBioRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindBioRepo(impl: BioRepository): IBioRepo
}
