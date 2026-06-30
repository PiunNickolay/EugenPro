package com.example.eugenpro.data.repository

import com.example.eugenpro.data.local.ExerciseDao
import com.example.eugenpro.data.mapper.toDomain
import com.example.eugenpro.data.mapper.toEntity
import com.example.eugenpro.domain.model.Exercise
import com.example.eugenpro.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao
): ExerciseRepository {
    override fun getAllExercise(): Flow<List<Exercise>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertExercise(exercise: Exercise) {
        dao.insertExercise(exercise.toEntity())
    }

    override suspend fun updateExercise(exercise: Exercise) {
        dao.update(exercise.toEntity())
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        dao.delete(exercise.toEntity())
    }

    override suspend fun getById(id: Long): Exercise {
        return dao.getById(id)!!.toDomain()
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}