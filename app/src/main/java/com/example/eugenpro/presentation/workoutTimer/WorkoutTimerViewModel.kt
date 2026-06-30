package com.example.eugenpro.presentation.workoutTimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eugenpro.domain.Exercise
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutTimerViewModel : ViewModel() {

    private val _timer = MutableStateFlow(Timer.initial())
    val timer: StateFlow<Timer> = _timer.asStateFlow()

    private var exercises: List<Exercise> = emptyList()

    fun startWorkout(exercises: List<Exercise>) {
        if (exercises.isEmpty()) return

        if (this.exercises.isNotEmpty()) return

        this.exercises = exercises
        val firstExercise = exercises.first()
        val totalTime = calculateTotalWorkoutTime(exercises)

        _timer.value = Timer(
            currentExerciseIndex = 0,
            currentRepetition = 1,
            phase = WorkoutPhase.Work,
            remainingTime = firstExercise.exerciseTime.toIntOrNull() ?: 30,
            isRunning = false,
            totalExercises = exercises.size,
            totalWorkoutTime = totalTime,
            elapsedTime = 0,
            isFinished = false
        )
    }

    fun toggleTimer() {
        val current = _timer.value
        if (current.isFinished) return

        _timer.value = current.copy(
            isRunning = !current.isRunning
        )

        if (_timer.value.isRunning) {
            startTimerTick()
        }
    }

    private fun startTimerTick() {
        viewModelScope.launch {
            while (_timer.value.isRunning && !_timer.value.isFinished) {
                delay(1000)
                tick()
            }
        }
    }

    private fun tick() {
        val current = _timer.value
        if (!current.isRunning || current.isFinished) return

        val newRemaining = current.remainingTime - 1
        val newElapsed = current.elapsedTime + 1

        if (newRemaining > 0) {
            _timer.value = current.copy(
                remainingTime = newRemaining,
                elapsedTime = newElapsed
            )
        } else {
            moveToNextPhase(newElapsed)
        }
    }

    fun skip() {
        val current = _timer.value
        if (current.isFinished) return
        moveToNextPhase(current.elapsedTime)
    }

    private fun moveToNextPhase(elapsedTime: Int) {
        val current = _timer.value
        val exercise = exercises.getOrNull(current.currentExerciseIndex)

        if (exercise == null) {
            finishWorkout(elapsedTime)
            return
        }

        when (current.phase) {
            WorkoutPhase.Work -> handleWorkPhaseEnd(exercise, current, elapsedTime)
            WorkoutPhase.RestBetweenSets -> handleRestBetweenEnd(exercise, current, elapsedTime)
            WorkoutPhase.RestAfterExercise -> handleRestAfterEnd(current, elapsedTime)
        }
    }

    private fun handleWorkPhaseEnd(
        exercise: Exercise,
        current: Timer,
        elapsedTime: Int
    ) {
        val isLastRepetition = current.currentRepetition >= exercise.exerciseRepetitions
        val isLastExercise = current.currentExerciseIndex >= exercises.size - 1

        if (isLastRepetition) {
            if (isLastExercise) {
                finishWorkout(elapsedTime)
            } else {
                val restTime = exercise.restTimeAfterExercise.toIntOrNull() ?: 30
                _timer.value = current.copy(
                    phase = WorkoutPhase.RestAfterExercise,
                    remainingTime = restTime,
                    elapsedTime = elapsedTime
                )
            }
        } else {
            val restTime = exercise.restTime.toIntOrNull() ?: 10
            _timer.value = current.copy(
                phase = WorkoutPhase.RestBetweenSets,
                remainingTime = restTime,
                elapsedTime = elapsedTime
            )
        }
    }

    private fun handleRestBetweenEnd(
        exercise: Exercise,
        current: Timer,
        elapsedTime: Int
    ) {
        _timer.value = current.copy(
            phase = WorkoutPhase.Work,
            currentRepetition = current.currentRepetition + 1,
            remainingTime = exercise.exerciseTime.toIntOrNull() ?: 30,
            elapsedTime = elapsedTime
        )
    }

    private fun handleRestAfterEnd(
        current: Timer,
        elapsedTime: Int
    ) {
        val nextIndex = current.currentExerciseIndex + 1
        val nextExercise = exercises.getOrNull(nextIndex)

        if (nextExercise != null) {
            _timer.value = current.copy(
                currentExerciseIndex = nextIndex,
                currentRepetition = 1,
                phase = WorkoutPhase.Work,
                remainingTime = nextExercise.exerciseTime.toIntOrNull() ?: 30,
                elapsedTime = elapsedTime
            )
        } else {
            finishWorkout(elapsedTime)
        }
    }

    private fun finishWorkout(elapsedTime: Int) {
        _timer.value = _timer.value.copy(
            isRunning = false,
            isFinished = true,
            remainingTime = 0,
            elapsedTime = elapsedTime
        )
    }

    private fun calculateTotalWorkoutTime(exercises: List<Exercise>): Int {
        var total = 0
        exercises.forEachIndexed { index, exercise ->
            val workTime = exercise.exerciseTime.toIntOrNull() ?: 30
            val restBetween = exercise.restTime.toIntOrNull() ?: 10
            val restAfter = exercise.restTimeAfterExercise.toIntOrNull() ?: 30
            val reps = exercise.exerciseRepetitions

            total += workTime * reps

            if (reps > 1) {
                total += restBetween * (reps - 1)
            }

            if (index < exercises.size - 1) {
                total += restAfter
            }
        }
        return total
    }

    fun getCurrentExerciseName(): String {
        val current = _timer.value
        return when (current.phase) {
            WorkoutPhase.Work -> {
                exercises.getOrNull(current.currentExerciseIndex)?.exerciseName ?: ""
            }
            WorkoutPhase.RestBetweenSets -> "Перерыв"
            WorkoutPhase.RestAfterExercise -> "Отдых"
        }
    }

    fun getNextTitle(): String? {
        val current = _timer.value
        return when (current.phase) {
            WorkoutPhase.Work -> null
            WorkoutPhase.RestBetweenSets -> {
                exercises.getOrNull(current.currentExerciseIndex)?.exerciseName
            }
            WorkoutPhase.RestAfterExercise -> {
                exercises.getOrNull(current.currentExerciseIndex + 1)?.exerciseName
            }
        }
    }

    fun getCircleProgress(): Float {
        val current = _timer.value
        val exercise = exercises.getOrNull(current.currentExerciseIndex) ?: return 0f

        val totalTime = when (current.phase) {
            WorkoutPhase.Work -> exercise.exerciseTime.toIntOrNull() ?: 30
            WorkoutPhase.RestBetweenSets -> exercise.restTime.toIntOrNull() ?: 10
            WorkoutPhase.RestAfterExercise -> exercise.restTimeAfterExercise.toIntOrNull() ?: 30
        }

        return if (totalTime > 0) {
            (totalTime - current.remainingTime).toFloat() / totalTime
        } else 0f
    }

    fun getLinearProgress(): Float {
        val current = _timer.value
        return if (current.totalWorkoutTime > 0) {
            current.elapsedTime.toFloat() / current.totalWorkoutTime
        } else 0f
    }

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    fun reset() {
        _timer.value = Timer.initial()
        exercises = emptyList()
    }
}