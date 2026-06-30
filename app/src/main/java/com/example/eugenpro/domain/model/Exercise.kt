package com.example.eugenpro.domain.model

data class Exercise(
    val id: Long,
    val exerciseName: String,
    val exerciseTime: String,
    val exerciseRepetitions: Int,
    val restTime: String,
    val restTimeAfterExercise: String
)