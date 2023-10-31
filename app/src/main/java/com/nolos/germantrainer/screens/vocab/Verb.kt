package com.nolos.germantrainer.screens.vocab

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Verb(
    override val english: String = "",
    override val german: String = "",
    override val categories: MutableList<String> = mutableListOf()
) : IWord
