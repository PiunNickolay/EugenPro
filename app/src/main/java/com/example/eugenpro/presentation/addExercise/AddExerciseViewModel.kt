package com.example.eugenpro.presentation.addExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eugenpro.domain.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddExerciseViewModel : ViewModel() {
    private var editExerciseId: Int? = null
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _workTime = MutableStateFlow(30)
    val workTime: StateFlow<Int> = _workTime.asStateFlow()

    private val _restTime = MutableStateFlow(30)
    val restTime: StateFlow<Int> = _restTime.asStateFlow()

    private val _repetitions = MutableStateFlow(1)
    val repetitions: StateFlow<Int> = _repetitions.asStateFlow()

    fun updateName(newName: String) {
        if (newName.isNotBlank()) _name.value = newName
    }

    fun updateWorkTimer(time: Int) {
        if (time > 0) _workTime.value = time
    }

    fun updateRestTime(time: Int) {
        if (time > 0) _restTime.value = time
    }

    fun updateRepetitions(count: Int) {
        if (count > 0) _repetitions.value = count
    }

    fun isValid(): Boolean {
        return _name.value.isNotBlank() &&
                _workTime.value > 0 &&
                _restTime.value > 0 &&
                _repetitions.value > 0
    }

    fun createExercise(): Exercise? {
        if (!isValid()) return null

        return if (editExerciseId != null) {
            Exercise(
                id = editExerciseId!!,
                exerciseName = _name.value,
                exerciseTime = _workTime.value.toString(),
                exerciseRepetitions = _repetitions.value,
                restTime = _restTime.value.toString()
            )
        } else {
            Exercise(
                id = System.currentTimeMillis().toInt(),
                exerciseName = _name.value,
                exerciseTime = _workTime.value.toString(),
                exerciseRepetitions = _repetitions.value,
                restTime = _restTime.value.toString()
            )
        }
    }

    fun loadExerciseForEdit(exercise: Exercise) {
        editExerciseId = exercise.id
        _name.value = exercise.exerciseName
        _workTime.value = exercise.exerciseTime.toIntOrNull() ?: 30
        _restTime.value = exercise.restTime.toIntOrNull() ?: 30
        _repetitions.value = exercise.exerciseRepetitions
    }

    fun resetForNewExercise() {
        editExerciseId = null
        _name.value = ""
        _workTime.value = 30
        _restTime.value = 30
        _repetitions.value = 1
    }
}