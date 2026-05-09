package com.suryashakti.repository

import com.suryashakti.data.SolarLogDao
import com.suryashakti.data.SolarLog
import kotlinx.coroutines.flow.Flow

class SolarRepository(private val solarLogDao: SolarLogDao) {
    fun getRecentLogs(): Flow<List<SolarLog>> = solarLogDao.getRecentLogs()
    
    fun getLogByDate(date: String): Flow<SolarLog?> = solarLogDao.getLogByDate(date)
    
    suspend fun insertLog(log: SolarLog) {
        solarLogDao.insert(log)
    }
}
