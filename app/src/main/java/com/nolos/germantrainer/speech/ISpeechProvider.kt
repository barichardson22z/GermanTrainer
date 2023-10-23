package com.nolos.germantrainer.speech

import java.util.Locale

interface ISpeechProvider {
    fun setLanguage(locale: Locale)
    fun speak(text: String)
}