package com.nolos.germantrainer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController


@Composable
fun TrainingScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Training")
        LazyColumn() {
            TrainingScreens.values().forEach { screen ->
                item {
                    Button(modifier = Modifier.fillMaxSize(),
                        onClick = { navController.navigate(screen.route) }) {
                        Text(text = stringResource(id = screen.stringId))
                    }
                }
            }

        }
    }


}
