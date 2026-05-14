# Surya-Shakti Solar Monitor ☀️⚡

An Android-based solar energy monitoring application designed for households in rural and semi-urban India. The app helps users track solar energy generation, electricity consumption, savings, and energy independence through an intuitive dashboard and offline-first architecture.

---

## 📱 Project Overview

**Surya-Shakti Solar Monitor** transforms raw solar and electricity readings into meaningful insights that help users:

* Monitor daily solar power generation
* Track electricity consumption
* Calculate savings in INR
* Measure green energy independence
* Detect surplus energy exported to the grid
* Receive peak sunlight appliance usage suggestions

The application is designed with a **high-contrast UI**, offline support, and simple user interaction for accessibility and ease of use.

---

## 🚀 Features

### ✅ MVP Features

* 🌞 Daily solar generation logging
* ⚡ Electricity consumption tracking
* 📊 Green Energy Independence Score
* 💰 Net savings calculation
* 🔋 Grid export detection and calculation
* 🔔 Peak sun-hour notifications
* 💾 Offline Room Database storage
* 🎨 High-contrast Yellow/Black UI

### 🌟 Future Enhancements

* 🤖 GenAI chatbot assistant
* 📈 30-day savings analytics charts
* 📂 CSV data export
* 🌐 Hindi language support
* 🏠 Home-screen widget
* 🧠 AI appliance scheduler
* 🌍 Carbon offset tracker

---

## 🛠️ Tech Stack

| Component     | Technology                       |
| ------------- | -------------------------------- |
| Language      | Kotlin                           |
| Architecture  | MVVM + Repository Pattern        |
| UI            | Jetpack Compose                  |
| Database      | Room DB (SQLite)                 |
| Notifications | WorkManager + NotificationCompat |
| Charts        | MPAndroidChart                   |
| GenAI         | Gemini API                       |
| Min SDK       | API 24                           |
| Target SDK    | API 34                           |

---

## 🧩 Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture for maintainability and scalability.

```text
UI (Compose Screens)
       ↓
ViewModel
       ↓
Repository
       ↓
Room Database
```

---

## 📂 Project Structure

```text
app/
├── data/
│   ├── local/
│   ├── repository/
│   └── model/
├── ui/
│   ├── screens/
│   ├── components/
│   └── theme/
├── viewmodel/
├── worker/
├── utils/
└── MainActivity.kt
```

---

## ⚙️ Functional Modules

### 1. Solar Generation Logging

Users can manually enter daily solar generation values in kWh or simulate generation using weather conditions such as:

* Sunny
* Cloudy
* Partly Cloudy

---

### 2. Consumption Tracking

Users enter electricity meter readings, and the app calculates:

```text
Net Usage = Solar Generation − Consumption
```

---

### 3. Independence Score

The app computes a Green Energy Independence Score:

\text{Independence Score} = \left(\frac{\text{Solar Generation}}{\text{Total Consumption}}\right) \times 100

#### Score Categories

* 0–40% → Dependent
* 41–70% → Transitioning
* 71–100% → Independent

---

### 4. Savings Calculation

Daily savings are calculated using:

\text{Net Savings (INR)} = \text{Net Solar Units} \times \text{Per Unit Rate}

Default electricity rate: **₹8/kWh**

---

### 5. Grid Export Handling

If generation exceeds consumption:

```text
Generation > Consumption
```

The surplus is marked as **Grid Export**, and estimated export value is calculated.

---

## 📊 Non-Functional Requirements

* ⚡ App launch under 2 seconds
* 📱 Dashboard render under 500 ms
* 🌐 Fully offline operation
* 🔒 No personal data collection
* ♿ WCAG AA accessibility compliance
* 🧱 Clean Architecture implementation
* 🌍 Future-ready localization support

---

## 🔔 Notifications

The app sends scheduled alerts during peak sunlight hours:

```text
"High Sun: Ideal time for heavy appliances."
```

Implemented using:

* WorkManager
* NotificationCompat

---

## 💾 Data Persistence

All energy logs are stored locally using **Room Database**.

Features include:

* Offline data storage
* Historical analysis
* 90-day trend tracking
* App restart persistence

---

## 📈 Success Criteria

The project aims to achieve:

* Accurate savings calculations
* Reliable notification delivery
* Fast dashboard rendering
* Persistent offline storage
* Correct surplus energy handling
* Readable outdoor-friendly UI

---

## 🔮 GenAI Integration

Future versions may integrate a Gemini-powered AI assistant capable of answering questions like:

* “Why is my energy score low today?”
* “When should I run heavy appliances?”
* “How can I improve solar efficiency?”

---

## 📷 Screens Included

* Dashboard Screen
* Daily Log Screen
* Savings Report Screen
* Settings Screen
* Notification Preferences

---

## ▶️ Getting Started

### Prerequisites

* Android Studio Hedgehog or later
* Kotlin support
* Android SDK 24+
* Gradle 8+

---

### Installation

```bash
git clone <repository-url>
```

Open the project in Android Studio and run:

```bash
Sync Project with Gradle Files
```

Then launch on emulator or device.

---

## 👨‍💻 Author

Developed as part of the **Android App Development using GenAI** project under the Energy domain.

---

## 📜 License

This project is intended for educational and academic purposes.
