package com.nolos.germantrainer.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.nolos.germantrainer.R

enum class NavScreens(
    val route: String,
    @StringRes val stringId: Int,
    val icon: ImageVector
) {
    HOME("home", R.string.nav_home_label, icon = Icons.Filled.Home),
    TRAINING("training", R.string.nav_training_label, icon = Icons.Filled.Star),
    GAMES("games", R.string.nav_games_label, icon = Icons.Filled.CheckCircle),
    SETTINGS("settings", R.string.nav_settings_label, Icons.Filled.Settings)
}

enum class TrainingScreens(
    val route: String,
    @StringRes val stringId: Int
) {
    VOCAB("${NavScreens.TRAINING.route}/vocab", R.string.nav_vocab_label),
    CONJUGATION("${NavScreens.TRAINING.route}/conjugation", R.string.nav_conj_label)
}