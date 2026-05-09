package com.suryashakti.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository
import com.suryashakti.ui.theme.BlackSurface
import com.suryashakti.ui.theme.YellowMain
import com.suryashakti.viewmodel.SolarViewModel
import com.suryashakti.viewmodel.SolarViewModelFactory

@Composable
fun DashboardScreen(
    repository: SolarRepository,
    settingsRepository: SettingsRepository,
    navController: NavController
) {
    val viewModel: SolarViewModel = viewModel(factory = SolarViewModelFactory(repository, settingsRepository))
    val todayLog by viewModel.todayLog.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Today's Overview",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = YellowMain,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (todayLog == null) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = BlackSurface)
            ) {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No data logged today.", color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("entry") },
                        colors = ButtonDefaults.buttonColors(containerColor = YellowMain)
                    ) {
                        Text("Add Data", color = Color.Black)
                    }
                }
            }
        } else {
            val log = todayLog!!
            val indScore = if (log.consumptionKwh > 0) {
                ((log.generationKwh / log.consumptionKwh) * 100).coerceIn(0.0, 100.0).toInt()
            } else 100

            val statusText = when (indScore) {
                in 0..40 -> "Dependent"
                in 41..70 -> "Transitioning"
                else -> "Independent"
            }

            IndependenceCard(indScore, statusText)

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard("Generation", "${log.generationKwh} kWh", YellowMain, Modifier.weight(1f))
                MetricCard("Consumption", "${log.consumptionKwh} kWh", Color.Gray, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            MetricCard("Net Usage", "${log.netUsage} kWh", if (log.netUsage <= 0) Color.Green else Color.Red, Modifier.fillMaxWidth())

            if (log.gridExportKwh > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                MetricCard("Grid Export Surplus", "${log.gridExportKwh} kWh", Color.Green, Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))
            MetricCard("Savings Today", "₹ ${String.format("%.2f", log.savingsInr)}", YellowMain, Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("report") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = YellowMain)
            ) {
                Text("View 30-Day Report", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BlackSurface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            Text(value, color = color, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun IndependenceCard(score: Int, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BlackSurface)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(
                        color = Color.DarkGray,
                        startAngle = 135f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = 24f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = Color(0xFFFFD600),
                        startAngle = 135f,
                        sweepAngle = 270f * (score / 100f),
                        useCenter = false,
                        style = Stroke(width = 24f, cap = StrokeCap.Round)
                    )
                }
                Text("$score%", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Text("Green Energy", color = Color.Gray)
                Text("Independence", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = if (score > 70) Color(0xFF1B5E20) else if (score > 40) Color(0xFFF57F17) else Color(0xFFB71C1C),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(status, color = Color.White, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
