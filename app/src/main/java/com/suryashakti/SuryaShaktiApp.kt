package com.suryashakti

import android.app.Application
import com.suryashakti.data.AppDatabase
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository

class SuryaShaktiApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { SolarRepository(database.solarLogDao()) }
    val settingsRepository by lazy { SettingsRepository(this) }
}
