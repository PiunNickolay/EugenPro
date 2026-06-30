package com.example.eugenpro.data.mapper

import com.example.eugenpro.data.local.ExerciseEntity
import com.example.eugenpro.domain.model.Exercise

fun ExerciseEntity.toDomain(): Exercise = Exercise(
    id = id,
    exerciseName = exerciseName,
    exerciseTime = exerciseTime,
    exerciseRepetitions = exerciseRepetitions,
    restTime = restTime,
    restTimeAfterExercise = restTimeAfterExercise
)

fun Exercise.toEntity(): ExerciseEntity = ExerciseEntity(
    id = id,
    exerciseName = exerciseName,
    exerciseTime = exerciseTime,
    exerciseRepetitions = exerciseRepetitions,
    restTime = restTime,
    restTimeAfterExercise = restTimeAfterExercise
)