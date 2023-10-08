package com.nolos.germantrainer.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.nolos.germantrainer.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector)  {
    object HomeScreen : Screen("home_screen", R.string.nav_home_label, Icons.Filled.Home)
    object TrainingScreen : Screen("training_screen", R.string.nav_training_label, Icons.Filled.Star)
    object GamesScreen : Screen("games_screen", R.string.nav_games_label, Icons.Filled.CheckCircle)
    object SettingsScreen : Screen("settings_screen", R.string.nav_settings_label, Icons.Filled.Settings)
}
