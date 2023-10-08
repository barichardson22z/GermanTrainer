package com.nolos.germantrainer.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TrainingScreen() {
    Text(text = "Training")
}

enum class TrainingOptions(val title: String, val desc: String, val icon: ImageVector) {
    MENU_VOCAB("Vocab", "Practice Vocabulary", Icons.Default.Star),
    MENU_CONJUGATION("Conjugation", "Practice Conjugation of Regular and Irregular Verbs", Icons.Default.Build),
    MENU_SENTENCES("Structure", "Practice Sentence Structure", Icons.Default.AddCircle),
    MENU_PHRASES("Phrases", "Practice Some Common German Phrases", Icons.Default.Create),
    MENU_IDIOMS("Idioms", "Practice Some Common German Idioms", Icons.Default.Done),
}