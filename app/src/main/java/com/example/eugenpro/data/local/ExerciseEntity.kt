package com.example.eugenpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey val id: Long,
    val exerciseName: String,
    val exerciseTime: String,
    val exerciseRepetitions: Int,
    val restTime: String,
    val restTimeAfterExercise: String
)
