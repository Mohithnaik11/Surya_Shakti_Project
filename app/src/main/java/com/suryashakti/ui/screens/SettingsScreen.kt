package com.suryashakti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.suryashakti.SuryaShaktiApp
import com.suryashakti.ui.theme.BlackSurface
import com.suryashakti.ui.theme.YellowMain
import com.suryashakti.utils.CsvExporter
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val settingsRepo = (context.applicationContext as SuryaShaktiApp).settingsRepository

    val savedUtilRate by settingsRepo.utilityRate.collectAsState(initial = 8.0)
    val savedExportTariff by settingsRepo.exportTariff.collectAsState(initial = 4.0)

    var notifsEnabled by remember { mutableStateOf(true) }
    var exportTariff by remember(savedExportTariff) { mutableStateOf(savedExportTariff.toString()) }
    var utilRate by remember(savedUtilRate) { mutableStateOf(savedUtilRate.toString()) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
                "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = YellowMain,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(colors = CardDefaults.cardColors(containerColor = BlackSurface), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Configuration", color = YellowMain, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = utilRate,
                        onValueChange = { utilRate = it },
                        label = { Text("Cost per unit (INR/kWh)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = YellowMain)
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = exportTariff,
                        onValueChange = { exportTariff = it },
                        label = { Text("Feed-in Tariff (INR/kWh)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = YellowMain)
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val rate = utilRate.toDoubleOrNull()
                            val tariff = exportTariff.toDoubleOrNull()
                            if (rate != null && tariff != null) {
                                scope.launch {
                                    settingsRepo.saveUtilityRate(rate)
                                    settingsRepo.saveExportTariff(tariff)
                                    snackbarHostState.showSnackbar("Settings saved!")
                                }
                            } else {
                                scope.launch { snackbarHostState.showSnackbar("Please enter valid numbers.") }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = YellowMain)
                    ) {
                        Text("Save Settings", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Card(colors = CardDefaults.cardColors(containerColor = BlackSurface), modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Peak Sun Notifications", color = Color.White)
                        Text("10 AM - 2 PM Reminders", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = notifsEnabled,
                        onCheckedChange = { notifsEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = YellowMain, checkedTrackColor = Color(0xFFC7A500))
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        CsvExporter.export(context)
                        snackbarHostState.showSnackbar("Exported to Downloads folder!")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Export Data to CSV", color = Color.White)
            }
        }
    }
}
