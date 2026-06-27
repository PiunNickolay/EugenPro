package com.example.eugenpro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eugenpro.presentation.addExercise.AddExerciseScreen
import com.example.eugenpro.presentation.mainScreen.MainScreen
import com.example.eugenpro.presentation.settings.SettingsScreen
import com.example.eugenpro.presentation.workoutTimer.WorkoutTimerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(Routes.MainScreen.route) {
            MainScreen(
                viewModel = viewModel(),
                onNavigateToSettingsScreen = { navController.navigate(Routes.Settings.route) },
                onNavigateToWorkoutScreen = { navController.navigate(Routes.WorkoutTimer.route) },
                onNavigateToAddExerciseScreen = { navController.navigate(Routes.AddExerciseScreen.route) }
            )
        }

        composable(Routes.Settings.route) {
            SettingsScreen(
                onNavigateToMainScreen = { navController.navigate(Routes.MainScreen.route) }
            )
        }

        composable(Routes.AddExerciseScreen.route) {
            AddExerciseScreen(
                onNavigateToMainScreen = { navController.navigate(Routes.MainScreen.route) }
            )
        }

        composable(Routes.WorkoutTimer.route) {
            WorkoutTimerScreen(
                onNavigateToMainScreen = { navController.navigate(Routes.MainScreen.route) }
            )
        }
    }
}