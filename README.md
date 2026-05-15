# ☀️ Surya-Shakti Solar Monitor

An Android application designed to help residential solar panel owners monitor solar energy generation, electricity consumption, savings, and energy independence using an intuitive and offline-first dashboard.

---

# 📱 Overview

Surya-Shakti Solar Monitor is a GenAI-powered Android application developed for households in rural and semi-urban India. The application transforms raw solar and electricity data into meaningful visual insights that help users optimize energy usage and reduce electricity costs.

The app allows users to:

* Track daily solar energy generation
* Monitor electricity consumption
* Calculate energy savings in INR
* Detect surplus power exported to the grid
* View energy independence scores
* Receive peak sunlight usage notifications
* Store data locally using Room Database
* Interact with an AI chatbot for energy-related guidance

---

# ✨ Features

## ✅ Core Features

### 🌞 Solar Generation Logging

* Manual entry of daily solar generation (kWh)
* Weather-based generation simulation
* Sunny / Cloudy / Partly Cloudy modes

### ⚡ Consumption Tracking

* Meter reading input system
* Automatic net energy calculation
* Real-time usage insights

### 📊 Green Energy Independence Score

* Circular progress visualization
* Percentage-based independence tracking
* Categorized energy status:

  * Dependent
  * Transitioning
  * Independent

### 💰 Savings Calculation

* Calculates estimated savings in INR
* Configurable electricity unit rate
* Monthly savings tracking

### 🔋 Grid Export Detection

* Detects surplus solar generation
* Calculates export value in kWh and INR

### 🔔 Peak Sun Notifications

* Sends appliance usage reminders
* Suggests optimal time for heavy appliances
* User-configurable notifications

### 💾 Offline Storage

* Local Room Database integration
* Stores historical energy logs
* Works fully offline

### 🤖 AI Chatbot

* Gemini-powered assistant
* Answers energy-related questions
* Provides smart suggestions and insights

### 📂 CSV Export

* Export all logs to CSV format
* Useful for spreadsheet analysis and reporting

---

# 🛠️ Tech Stack

| Component      | Technology                       |
| -------------- | -------------------------------- |
| Language       | Kotlin                           |
| UI Framework   | Jetpack Compose                  |
| Architecture   | MVVM + Repository Pattern        |
| Database       | Room Database                    |
| Notifications  | WorkManager + NotificationCompat |
| AI Integration | Gemini API                       |
| Data Storage   | DataStore Preferences            |
| Charts         | Compose UI Components            |
| Minimum SDK    | API 24                           |
| Target SDK     | API 34                           |

---

# 🧩 Project Architecture

The project follows the MVVM (Model-View-ViewModel) architecture.

```text
UI Layer (Compose Screens)
        ↓
ViewModel Layer
        ↓
Repository Layer
        ↓
Room Database
```

---

# 📂 Project Structure

```text
app/src/main/java/com/suryashakti/
│
├── api/
│   └── GeminiApi.kt
│
├── data/
│   ├── AppDatabase.kt
│   ├── SolarLog.kt
│   ├── SolarLogDao.kt
│   └── SettingsDataStore.kt
│
├── repository/
│   └── SolarRepository.kt
│
├── ui/
│   ├── components/
│   ├── screens/
│   └── theme/
│
├── utils/
│   └── CsvExporter.kt
│
├── viewmodel/
│   ├── ChatViewModel.kt
│   └── SolarViewModel.kt
│
├── worker/
│   └── SunNotificationWorker.kt
│
├── MainActivity.kt
└── SuryaShaktiApp.kt
```

---

# 📊 Functional Modules

## 1. Energy Generation Module

Users manually enter or simulate solar power generation values.

## 2. Consumption Module

Tracks household electricity usage and calculates net energy.

## 3. Savings Module

Computes estimated financial savings based on generated solar energy.

## 4. Dashboard Module

Displays:

* Independence Score
* Savings
* Generation vs Consumption
* Exported Energy

## 5. Notification Module

Uses WorkManager to schedule peak sunlight reminders.

## 6. AI Assistant Module

Provides smart recommendations and answers user queries.

---

# ⚙️ Installation

## Prerequisites

* Android Studio Hedgehog or later
* Kotlin support enabled
* Android SDK 24+
* Gradle 8+

---

## Clone the Repository

```bash
git clone <repository-url>
```

---

## Open in Android Studio

1. Open Android Studio
2. Select **Open Project**
3. Choose the project folder
4. Sync Gradle files
5. Run the application on emulator or device

---

# ▶️ Running the App

1. Connect Android device or start emulator
2. Click **Run ▶** in Android Studio
3. Launch the application

---

# 🔐 Permissions Used

| Permission         | Purpose                |
| ------------------ | ---------------------- |
| POST_NOTIFICATIONS | Peak sun alerts        |
| INTERNET           | Gemini API integration |

---

# 📈 Future Enhancements

* Hindi language support
* Dark/Light mode switching
* Carbon footprint tracking
* Home-screen widgets
* Smart appliance scheduling
* Real-time IoT solar inverter integration

---

# 🎯 Impact Goals

* Promote renewable energy adoption
* Improve household energy awareness
* Reduce dependency on traditional electricity
* Increase financial savings through solar optimization

---

# 🧪 Testing Goals

The application aims to ensure:

* Accurate savings calculations
* Reliable notification scheduling
* Persistent offline data storage
* Smooth dashboard performance
* Proper export handling
* Accessible high-contrast UI

---

# 👨‍💻 Author

Developed as part of the **Android App Development using GenAI** project in the Energy Domain.

---

# 📜 License

This project is developed for educational and academic purposes.



