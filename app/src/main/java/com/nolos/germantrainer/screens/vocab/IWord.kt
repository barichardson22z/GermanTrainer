package com.nolos.germantrainer.screens.vocab

interface IWord {
    val english: String
    val german: String
    val categories: MutableList<String>
}