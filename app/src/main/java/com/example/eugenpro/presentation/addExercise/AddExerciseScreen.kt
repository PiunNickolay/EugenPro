package com.example.eugenpro.presentation.addExercise


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eugenpro.domain.model.Exercise
import com.example.eugenpro.presentation.mainScreen.MainScreenViewModels


@Composable
fun AddExerciseScreen(
    viewModel: AddExerciseViewModel,
    onNavigateToMainScreen: () -> Unit,
    onSaveExercise: (Exercise) -> Unit,
    mainScreenViewModels: MainScreenViewModels,
    exerciseId: Long? = null
) {
    val name by viewModel.name.collectAsState()
    val workTime by viewModel.workTime.collectAsState()
    val restTime by viewModel.restTime.collectAsState()
    val repetitions by viewModel.repetitions.collectAsState()
    val restTimeAfterExercise by viewModel.restTimeAfterExercise.collectAsState()

    LaunchedEffect(exerciseId) {
        if (exerciseId != null) {
            val exercise = mainScreenViewModels.exercises.value.find { it.id == exerciseId }
            exercise?.let { viewModel.loadExerciseForEdit(it) }
        } else {
            viewModel.resetForNewExercise()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AddExerciseCard(
            onChangeName = { newName -> viewModel.updateName(newName) },
            name = name,
            workTime = workTime,
            restTime = restTime,
            repetitions = repetitions,
            restTimeAfterExercise = restTimeAfterExercise,
            onWorkTimeChange = { work -> viewModel.updateWorkTimer(work) },
            onRestTimeChange = { rest -> viewModel.updateRestTime(rest) },
            onRepetitionsChange = { repetitions -> viewModel.updateRepetitions(repetitions) },
            onRestTimeAfterExercise = { restAfter -> viewModel.updateRestTimeAfterExercise(restAfter) },
            onSave = {
                val exercise = viewModel.createExercise()
                if (exercise != null) {
                    mainScreenViewModels.addExercise(exercise)
                }
                onNavigateToMainScreen()
            },
            onCancel = {
                onNavigateToMainScreen()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
        )
    }

}
