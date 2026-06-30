package com.example.eugenpro.presentation.workoutTimer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eugenpro.R
import com.example.eugenpro.domain.model.Exercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTimerScreen(
    viewModel: WorkoutTimerViewModel,
    onNavigateToMainScreen: () -> Unit
) {
    val timerState by viewModel.timer.collectAsState()

    if (timerState.isFinished) {
        WorkoutFinishedScreen(onNavigateToMainScreen)
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = viewModel.getCurrentExerciseName(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = when (timerState.phase) {
                            WorkoutPhase.Work -> "WORK"
                            WorkoutPhase.RestBetweenSets -> "REST"
                            WorkoutPhase.RestAfterExercise -> "REST"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "Exercise ${timerState.currentExerciseIndex + 1} of ${timerState.totalExercises}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(250.dp)
            ) {
                CircularProgressIndicator(
                    progress = 1f,
                    modifier = Modifier.size(250.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )

                CircularProgressIndicator(
                    progress = viewModel.getCircleProgress(),
                    modifier = Modifier.size(250.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )

                Text(
                    text = viewModel.formatTime(timerState.remainingTime),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            viewModel.getNextTitle()?.let { next ->
                Text(
                    text = "Next: $next",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Button(
                    onClick = { viewModel.toggleTimer() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (timerState.isRunning) {
                                R.drawable.ic_pause
                            } else {
                                R.drawable.ic_play
                            }
                        ),
                        contentDescription = if (timerState.isRunning) "Pause" else "Play",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }

                Button(
                    onClick = { viewModel.skip() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_skip),
                        contentDescription = "Skip",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            LinearProgressIndicator(
                progress = viewModel.getLinearProgress(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )
        }

        IconButton(
            onClick = onNavigateToMainScreen,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun WorkoutFinishedScreen(onNavigateToMainScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Тренировка завершена!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Отличная работа!",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNavigateToMainScreen,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(52.dp)
        ) {
            Text("Вернуться на главный", fontSize = 16.sp)
        }
    }
}