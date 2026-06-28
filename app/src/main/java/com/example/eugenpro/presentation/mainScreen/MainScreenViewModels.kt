package com.example.eugenpro.presentation.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eugenpro.domain.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random
import kotlin.random.nextInt

class MainScreenViewModels: ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    fun addExercise(exercise: Exercise) {
        val existingIndex = _exercises.value.indexOfFirst { it.id == exercise.id }

        if (existingIndex >= 0) {
            updateExercise(exercise)
        } else {
            _exercises.value = _exercises.value + exercise
        }
    }

    fun removeExercise(exercise: Exercise) {
        _exercises.value = _exercises.value.filter { it.id != exercise.id}
    }

    fun updateExercise(updateExercise: Exercise) {
        _exercises.value = _exercises.value.map { exercise ->
            if (exercise.id == updateExercise.id) {
                updateExercise
            } else {
                exercise
            }
        }
    }

    fun clearExercises() {
        _exercises.value = emptyList()
    }
}