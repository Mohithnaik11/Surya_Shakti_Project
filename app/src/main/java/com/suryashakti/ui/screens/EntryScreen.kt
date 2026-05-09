package com.suryashakti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository
import com.suryashakti.ui.theme.BlackSurface
import com.suryashakti.ui.theme.YellowMain
import com.suryashakti.viewmodel.SolarViewModel
import com.suryashakti.viewmodel.SolarViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(repository: SolarRepository, settingsRepository: SettingsRepository) {
    val viewModel: SolarViewModel = viewModel(factory = SolarViewModelFactory(repository, settingsRepository))

    var generation by remember { mutableStateOf("") }
    var consumption by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf("Sunny") }
    var expanded by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Daily Log Entry",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = YellowMain,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = generation,
                onValueChange = { generation = it },
                label = { Text("Solar Generation (kWh)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = YellowMain,
                    focusedLabelColor = YellowMain
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = consumption,
                onValueChange = { consumption = it },
                label = { Text("Meter Reading / Consumption (kWh)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = YellowMain,
                    focusedLabelColor = YellowMain
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = weather,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Weather Condition") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = YellowMain,
                        focusedLabelColor = YellowMain
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(BlackSurface)
                ) {
                    listOf("Sunny", "Partly Cloudy", "Cloudy").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Color.White) },
                            onClick = {
                                weather = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val genD = generation.toDoubleOrNull()
                    val conD = consumption.toDoubleOrNull()
                    if (genD != null && conD != null) {
                        viewModel.addEntry(genD, conD, weather)
                        scope.launch {
                            snackbarHostState.showSnackbar("Logged Successfully!")
                            generation = ""
                            consumption = ""
                        }
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("Please enter valid numbers.") }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = YellowMain)
            ) {
                Text(
                    "Save Entry",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
