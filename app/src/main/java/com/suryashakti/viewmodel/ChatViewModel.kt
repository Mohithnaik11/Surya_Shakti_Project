package com.suryashakti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suryashakti.api.Content
import com.suryashakti.api.GeminiApi
import com.suryashakti.api.GeminiRequest
import com.suryashakti.api.Part
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {

    private val api = GeminiApi.create()
    private val apiKey = "AIzaSyCHG7oIkYDk0uO6o8isNOD4-TFB9whuzgE"

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        _messages.value = listOf(
            ChatMessage("Hi! I am your Surya-Shakti Solar Assistant. How can I help you improve your savings today?", false)
        )
    }

    fun sendMessage(text: String) {
        _messages.value = _messages.value + ChatMessage(text, true)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val prompt = """
                    You are a solar energy assistant for a rural/semi-urban user in India.
                    Help with questions about solar generation, savings, and best time to run appliances.
                    Give short, simple, actionable answers in plain language.
                    User asks: $text
                """.trimIndent()

                val request = GeminiRequest(listOf(Content(listOf(Part(prompt)))))
                val response = api.generateContent(apiKey, request)

                if (response.error != null) {
                    val code = response.error.code ?: 0
                    val msg = response.error.message ?: "Unknown error"
                    Log.e("ChatViewModel", "Gemini API error $code: $msg")
                    appendMessage("API Error $code: $msg")
                    return@launch
                }

                val aiText = response.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?: "Received empty response. Please try again."

                appendMessage(aiText)

            } catch (e: HttpException) {
                val code = e.code()
                Log.e("ChatViewModel", "HTTP $code: ${e.message}")
                appendMessage(when (code) {
                    401 -> "Invalid API key (401). Please check your key at aistudio.google.com"
                    403 -> "Access denied (403). Your key may not have Gemini API enabled."
                    404 -> "Model not found (404). The model name may be outdated."
                    429 -> "Rate limit hit (429). Wait a minute and try again."
                    else -> "HTTP error $code. Check Logcat for details."
                })
            } catch (e: UnknownHostException) {
                Log.e("ChatViewModel", "No internet: ${e.message}")
                appendMessage("No internet connection. Check your WiFi or mobile data.")
            } catch (e: SocketTimeoutException) {
                Log.e("ChatViewModel", "Timeout: ${e.message}")
                appendMessage("Request timed out. Please try again.")
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error: ${e.javaClass.simpleName}: ${e.message}")
                appendMessage("Error (${e.javaClass.simpleName}): ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun appendMessage(text: String) {
        _messages.value = _messages.value + ChatMessage(text, false)
    }
}