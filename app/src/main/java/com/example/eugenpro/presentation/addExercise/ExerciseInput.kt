package com.example.eugenpro.presentation.addExercise

data class ExerciseInput(
    val exerciseName: String,
    val workTime: Int,
    val restTime: Int,
    val restTimeAfterExercise: Int,
    val repetitions: Int
)
