package com.example.eugenpro.domain

data class Exercise(
    val id: Int,
    val exerciseName: String,
    val exerciseTime: String,
    val exerciseRepetitions: Int,
    val restTime: String,
    val restTimeAfterExercise: String
)
