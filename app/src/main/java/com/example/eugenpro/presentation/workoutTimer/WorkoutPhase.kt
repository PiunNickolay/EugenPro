package com.example.eugenpro.presentation.workoutTimer

sealed class WorkoutPhase {
    object Work: WorkoutPhase()
    object RestBetweenSets: WorkoutPhase()
    object RestAfterExercise: WorkoutPhase()
}