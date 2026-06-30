package com.example.eugenpro.presentation.workoutTimer

data class Timer(
    val currentExerciseIndex: Int,
    val currentRepetition: Int,
    val phase: WorkoutPhase,
    val remainingTime: Int,
    val isRunning: Boolean,
    val totalExercises: Int,
    val totalWorkoutTime: Int,
    val elapsedTime: Int,
    val isFinished: Boolean
) {
    companion object {
        fun initial() = Timer(
            currentExerciseIndex = 0,
            currentRepetition = 1,
            phase = WorkoutPhase.Work,
            remainingTime = 0,
            isRunning = false,
            totalExercises = 0,
            totalWorkoutTime = 0,
            elapsedTime = 0,
            isFinished = false
        )
    }
}
