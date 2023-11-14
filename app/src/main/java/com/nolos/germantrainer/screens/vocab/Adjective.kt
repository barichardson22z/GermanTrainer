package com.nolos.germantrainer.screens.vocab

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Adjective(
    override val english: String = "",
    override val german: String = "",
    override val categories: MutableList<String> = mutableListOf()
) : IWord
