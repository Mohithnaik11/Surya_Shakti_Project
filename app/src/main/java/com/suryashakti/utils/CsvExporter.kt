package com.suryashakti.utils

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

object CsvExporter {
    suspend fun export(context: Context) {
        withContext(Dispatchers.IO) {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "solar_logs.csv")
            
            try {
                FileWriter(file).use { writer ->
                    writer.append("Date,Generation,Consumption,Net,Savings,Weather\n")
                    // In a real app we fetch this from DB. Stubbing for compilation
                    writer.append("2023-11-01,15.2,10.0,-5.2,120.5,Sunny\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
