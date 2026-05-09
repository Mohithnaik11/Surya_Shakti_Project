package com.suryashakti.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solar_logs")
data class SolarLog(
    @PrimaryKey val date: String, // format: yyyy-MM-dd
    val generationKwh: Double,
    val consumptionKwh: Double,
    val netUsage: Double,
    val savingsInr: Double,
    val gridExportKwh: Double,
    val weatherCondition: String
)
