package com.example.eugenpro.data.di

import com.example.eugenpro.data.local.ExerciseDao
import com.example.eugenpro.data.repository.ExerciseRepositoryImpl
import com.example.eugenpro.domain.repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideExercise(
        dao: ExerciseDao
    ): ExerciseRepository = ExerciseRepositoryImpl(dao)
}