package com.example.eugenpro.domain.repository

import com.example.eugenpro.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getAllExercise(): Flow<List<Exercise>>

    suspend fun insertExercise(exercise: Exercise)

    suspend fun updateExercise(exercise: Exercise)

    suspend fun deleteExercise(exercise: Exercise)

    suspend fun getById(id: Long): Exercise

    suspend fun deleteAll()
}