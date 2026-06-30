package com.example.eugenpro.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eugenpro.R
import com.example.eugenpro.domain.model.Exercise
import com.example.eugenpro.ui.theme.EugenProTheme

@Composable
fun ExerciseCard(
    rejectClick: (Exercise) -> Unit,
    exercise: Exercise,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 4.dp,
            focusedElevation = 4.dp,
            hoveredElevation = 4.dp,
            draggedElevation = 4.dp,
            disabledElevation = 4.dp,
        ),
        modifier = Modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = exercise.exerciseName,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Icon(
                    painter = painterResource(R.drawable.ic_reject),
                    contentDescription = "Reject",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp)
                        .clickable { rejectClick(exercise) }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_exercise),
                    contentDescription = "Exercise"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Время выполнения упражнения",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = exercise.exerciseTime, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_repeat),
                    contentDescription = "Exercise"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Повторений",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = exercise.exerciseRepetitions.toString(), fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_time), contentDescription = "Exercise")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Перерыв между повторениями",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = exercise.restTime, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_rest_after_exercise),
                    contentDescription = "Rest after exercise"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Перерыв после упражнения",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = exercise.restTimeAfterExercise, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
@Preview
fun PreviewMainScreen() {
    EugenProTheme(darkTheme = true) {
        ExerciseCard(
            rejectClick = {/*...*/ },
            exercise = Exercise(
                1,
                "Hello",
                "30",
                1,
                "30",
                "30"
            ),
            onClick = {}
        )
    }
}
