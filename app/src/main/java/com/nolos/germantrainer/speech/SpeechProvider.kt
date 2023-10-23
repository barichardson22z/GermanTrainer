package com.nolos.germantrainer.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.Locale

class SpeechProvider(val context: Context) : ISpeechProvider, OnInitListener {

    private val tts: TextToSpeech = TextToSpeech(context, this)
    override fun setLanguage(locale: Locale) {
        tts.language = locale
    }

    override fun speak(text: String) {
        tts.setSpeechRate(1.0f)
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, null)
    }

    override fun onInit(p0: Int) {

    }
}