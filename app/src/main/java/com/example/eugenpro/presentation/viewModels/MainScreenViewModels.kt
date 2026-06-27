package com.example.eugenpro.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eugenpro.domain.Exercise
import kotlin.random.Random
import kotlin.random.nextInt

class MainScreenViewModels: ViewModel() {
    private val sourceList = mutableListOf<Exercise>().apply {
        repeat(10) {
            add(
                Exercise(
                    id = it,
                    exerciseName = "Exercise #$it",
                    exerciseTime = Random.nextInt().toString(),
                    exerciseRepetitions = Random.nextInt(1..5),
                    restTime = Random.nextInt(5).toString()
                )
            )
        }
    }

    private val _exercises = MutableLiveData<List<Exercise>>(sourceList)
    val exercises: LiveData<List<Exercise>> = _exercises

    fun removeExercise(exercise: Exercise) {
        val old = exercises.value?.toMutableList() ?:mutableListOf()
        old.remove(exercise)
        _exercises.value = old
    }
}