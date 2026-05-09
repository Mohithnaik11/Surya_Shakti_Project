package com.suryashakti.ui.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository
import com.suryashakti.ui.theme.BlackSurface
import com.suryashakti.ui.theme.YellowMain
import com.suryashakti.viewmodel.SolarViewModel
import com.suryashakti.viewmodel.SolarViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    repository: SolarRepository,
    settingsRepository: SettingsRepository,
    navController: NavController
) {
    val viewModel: SolarViewModel = viewModel(factory = SolarViewModelFactory(repository, settingsRepository))
    val recentLogs by viewModel.recentLogs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("30-Day Report", color = YellowMain) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = YellowMain)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )

        if (recentLogs.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No data available for charts.", color = Color.Gray)
            }
        } else {
            Column(Modifier.padding(16.dp)) {
                val totalSavings = recentLogs.take(30).sumOf { it.savingsInr }
                MetricCard(
                    "30-Day Cumulative Savings",
                    "₹ ${String.format("%.2f", totalSavings)}",
                    YellowMain,
                    Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text("Net Savings Trend", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    factory = { context ->
                        BarChart(context).apply {
                            description.isEnabled = false
                            legend.textColor = AndroidColor.WHITE
                            xAxis.apply {
                                position = XAxis.XAxisPosition.BOTTOM
                                textColor = AndroidColor.WHITE
                                setDrawGridLines(false)
                            }
                            axisLeft.apply {
                                textColor = AndroidColor.WHITE
                                setDrawGridLines(true)
                            }
                            axisRight.isEnabled = false
                        }
                    },
                    update = { chart ->
                        val entries = recentLogs.take(30).reversed().mapIndexed { index, log ->
                            BarEntry(index.toFloat(), log.savingsInr.toFloat())
                        }
                        val labels = recentLogs.take(30).reversed().map { it.date.substring(5) }

                        val dataSet = BarDataSet(entries, "Savings (INR)").apply {
                            color = AndroidColor.parseColor("#FFD600")
                            valueTextColor = AndroidColor.WHITE
                            valueTextSize = 10f
                        }
                        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                        chart.data = BarData(dataSet)
                        chart.invalidate()
                    }
                )
            }
        }
    }
}
