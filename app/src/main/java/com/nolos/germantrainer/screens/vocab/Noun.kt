package com.nolos.germantrainer.screens.vocab

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Noun(
    var english: String = "",
    var article: String = "",
    val german: String = "",
    val categories: MutableList<String> = mutableListOf()
)