package com.nolos.germantrainer.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.nolos.germantrainer.TrainingScreens


@Composable
fun TrainingScreen(navController: NavHostController) {
    Text(text = "Training")
    LazyRow() {
        TrainingScreens.values().forEach { screen ->
            item {
                IconButton(
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painterResource(id = screen.drawableId),
                        contentDescription = stringResource(id = screen.stringId)
                    )
                }
            }
        }

    }

}
