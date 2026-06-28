package com.example.eugenpro.presentation.navigation

import com.example.eugenpro.domain.Exercise

sealed class Routes(
    val route: String
) {
    object MainScreen: Routes(MAIN)
    object AddExerciseScreen: Routes(ADD_EXERCISE)
    object WorkoutTimer: Routes(WORKOUT)
    object Settings: Routes(SETTINGS)
    object EditExercise: Routes(EDIT_EXERCISE)

    companion object {
        const val MAIN = "main_screen"
        const val ADD_EXERCISE = "add_exercise_screen"
        const val WORKOUT = "workout_screen"
        const val SETTINGS = "settings_screen"
        const val EDIT_EXERCISE = "edit_exercise_screen/{exerciseId}"

        fun editExercise(exerciseId: Int) = "edit_exercise_screen/$exerciseId"
    }
}