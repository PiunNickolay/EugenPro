package com.example.eugenpro.presentation.addExercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eugenpro.R
import com.example.eugenpro.presentation.components.DurationPickerBottomSheet
import com.example.eugenpro.presentation.components.NumberPickerBottomSheet
import com.example.eugenpro.presentation.components.toDurationString
import com.example.eugenpro.ui.theme.EugenProTheme

@Composable
fun AddExerciseCard(
    onChangeName: (String) -> Unit,
    name: String,
    workTime: Int,
    restTime: Int,
    repetitions: Int,
    restTimeAfterExercise: Int,
    onWorkTimeChange: (Int) -> Unit,
    onRestTimeChange: (Int) -> Unit,
    onRepetitionsChange: (Int) -> Unit,
    onRestTimeAfterExercise: (Int) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {

    var showWorkTimePicker by remember { mutableStateOf(false) }
    var showRestTimePicker by remember { mutableStateOf(false) }
    var showRestAfterPicker by remember { mutableStateOf(false) }
    var showRepetitionsPicker by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth(0.95f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onChangeName,
                label = { Text("Название") },
                placeholder = { Text("Например: Отжимания") },
                singleLine = true,
                isError = name.isBlank(),
                supportingText = {
                    if (name.isBlank()) Text("Введите название")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            DurationCapsule(
                label = "Время упражнения",
                valueText = workTime.toDurationString(),
                onClick = { showWorkTimePicker = true }
            )

            NumberCapsule(
                label = "Повторения",
                valueText = repetitions.toString(),
                onClick = { showRepetitionsPicker = true }
            )

            DurationCapsule(
                label = "Отдых между подходами",
                valueText = restTime.toDurationString(),
                onClick = { showRestTimePicker = true }
            )

            DurationCapsule(
                label = "Отдых после упражнения",
                valueText = restTimeAfterExercise.toDurationString(),
                onClick = { showRestAfterPicker = true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Сохранить")
                }
                Button(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = "Отменить",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

    if (showWorkTimePicker) {
        DurationPickerBottomSheet(
            currentSeconds = workTime,
            title = "Время упражнения",
            onDismiss = { showWorkTimePicker = false },
            onConfirm = { seconds ->
                onWorkTimeChange(seconds)
                showWorkTimePicker = false
            }
        )
    }

    if (showRestTimePicker) {
        DurationPickerBottomSheet(
            currentSeconds = restTime,
            title = "Отдых между подходами",
            onDismiss = { showRestTimePicker = false },
            onConfirm = { seconds ->
                onRestTimeChange(seconds)
                showRestTimePicker = false
            }
        )
    }

    if (showRestAfterPicker) {
        DurationPickerBottomSheet(
            currentSeconds = restTimeAfterExercise,
            title = "Отдых после упражнения",
            onDismiss = { showRestAfterPicker = false },
            onConfirm = { seconds ->
                onRestTimeAfterExercise(seconds)
                showRestAfterPicker = false
            }
        )
    }

    if (showRepetitionsPicker) {
        NumberPickerBottomSheet(
            currentValue = repetitions,
            title = "Количество повторений",
            range = 1..50,
            onDismiss = { showRepetitionsPicker = false },
            onConfirm = { value ->
                onRepetitionsChange(value)
                showRepetitionsPicker = false
            }
        )
    }
}

@Composable
private fun DurationCapsule(
    label: String,
    valueText: String,
    onClick: () -> Unit
) {
    CapsuleContent(
        label = label,
        valueText = valueText,
        onClick = onClick
    )
}

@Composable
private fun NumberCapsule(
    label: String,
    valueText: String,
    onClick: () -> Unit
) {
    CapsuleContent(
        label = label,
        valueText = valueText,
        onClick = onClick
    )
}

@Composable
private fun CapsuleContent(
    label: String,
    valueText: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = valueText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_down),
                    contentDescription = "down"
                )
            }
        }
    }
}

@Composable
@Preview
fun CardExercisePreview() {
    EugenProTheme(darkTheme = true) {
        AddExerciseCard(
            onChangeName = {},
            name = "Hello",
            workTime = 30,
            onWorkTimeChange = {  },
            restTime = 15,
            restTimeAfterExercise = 15,
            onRestTimeChange = {  },
            repetitions = 1,
            onRepetitionsChange = { },
            onSave = {},
            onCancel = {},
            onRestTimeAfterExercise = {/*...*/ }
        )
    }
}