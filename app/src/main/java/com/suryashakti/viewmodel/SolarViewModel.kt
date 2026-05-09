package com.suryashakti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.suryashakti.data.SolarLog
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SolarViewModel(
    private val repository: SolarRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val recentLogs: StateFlow<List<SolarLog>> = repository.getRecentLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayLog: StateFlow<SolarLog?> = repository
        .getLogByDate(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _utilityRate = MutableStateFlow(8.0)
    private val _exportTariff = MutableStateFlow(4.0)

    init {
        viewModelScope.launch {
            settingsRepository.utilityRate.collect { _utilityRate.value = it }
        }
        viewModelScope.launch {
            settingsRepository.exportTariff.collect { _exportTariff.value = it }
        }
    }

    fun addEntry(generation: Double, consumption: Double, weather: String) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val netUsage = consumption - generation
        val gridExport = if (netUsage < 0) -netUsage else 0.0
        val selfConsumed = if (generation > consumption) consumption else generation
        val savings = (selfConsumed * _utilityRate.value) + (gridExport * _exportTariff.value)

        val log = SolarLog(
            date = today,
            generationKwh = generation,
            consumptionKwh = consumption,
            netUsage = netUsage,
            savingsInr = savings,
            gridExportKwh = gridExport,
            weatherCondition = weather
        )
        viewModelScope.launch { repository.insertLog(log) }
    }
}

class SolarViewModelFactory(
    private val repository: SolarRepository,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SolarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SolarViewModel(repository, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
