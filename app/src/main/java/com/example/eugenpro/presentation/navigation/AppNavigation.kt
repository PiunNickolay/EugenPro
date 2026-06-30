package com.example.eugenpro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eugenpro.presentation.addExercise.AddExerciseScreen
import com.example.eugenpro.presentation.addExercise.AddExerciseViewModel
import com.example.eugenpro.presentation.mainScreen.MainScreen
import com.example.eugenpro.presentation.mainScreen.MainScreenViewModels
import com.example.eugenpro.presentation.settings.SettingsScreen
import com.example.eugenpro.presentation.workoutTimer.WorkoutTimerScreen
import com.example.eugenpro.presentation.workoutTimer.WorkoutTimerViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val mainScreenViewModel: MainScreenViewModels = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(Routes.MainScreen.route) {
            MainScreen(
                viewModel = mainScreenViewModel,
                onNavigateToSettingsScreen = { navController.navigate(Routes.Settings.route) },
                onNavigateToWorkoutScreen = { navController.navigate(Routes.WorkoutTimer.route) },
                onNavigateToAddExerciseScreen = { navController.navigate(Routes.AddExerciseScreen.route) },
                onEditExerciseCard = { exerciseId ->
                    navController.navigate(Routes.editExercise(exerciseId))
                }
            )
        }

        composable(Routes.Settings.route) {
            SettingsScreen(
                onNavigateToMainScreen = { navController.popBackStack() }
            )
        }

        composable(Routes.AddExerciseScreen.route) {
            val addExerciseViewModel: AddExerciseViewModel = viewModel()
            AddExerciseScreen(
                viewModel = addExerciseViewModel,
                onSaveExercise = { exercise -> mainScreenViewModel.addExercise(exercise) },
                onNavigateToMainScreen = { navController.popBackStack() },
                mainScreenViewModels = mainScreenViewModel
            )
        }

        composable(
            route = Routes.EditExercise.route,
            arguments = listOf(navArgument("exerciseId") {type = NavType.LongType})
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getLong("exerciseId")
            val addExerciseViewModel: AddExerciseViewModel = viewModel()
            AddExerciseScreen(
                viewModel = addExerciseViewModel,
                mainScreenViewModels = mainScreenViewModel,
                exerciseId = exerciseId,
                onNavigateToMainScreen = { navController.popBackStack()},
                onSaveExercise = { exercise -> mainScreenViewModel.addExercise(exercise) }
            )
        }

        composable(Routes.WorkoutTimer.route) {

            val workoutTimerViewModel: WorkoutTimerViewModel = viewModel()

            LaunchedEffect(Unit) {
                workoutTimerViewModel.startWorkout(mainScreenViewModel.exercises.value)
            }

            WorkoutTimerScreen(
                viewModel = workoutTimerViewModel,
                onNavigateToMainScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}