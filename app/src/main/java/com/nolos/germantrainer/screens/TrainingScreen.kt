package com.nolos.germantrainer.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun TrainingScreen(navController: NavHostController) {
    Text(text = "Training")
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
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
