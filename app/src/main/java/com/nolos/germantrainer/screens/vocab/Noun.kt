package com.nolos.germantrainer.screens.vocab

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Noun (
    override val english: String = "",
    val article: String = "",
    override val german: String = "",
    override val categories: MutableList<String> = mutableListOf()
) : IWord