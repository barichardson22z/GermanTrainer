package com.nolos.germantrainer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nolos.germantrainer.screens.ConjugationScreen
import com.nolos.germantrainer.screens.GamesScreen
import com.nolos.germantrainer.screens.HomeScreen
import com.nolos.germantrainer.screens.NavScreens
import com.nolos.germantrainer.screens.SettingsScreen
import com.nolos.germantrainer.screens.TrainingScreen
import com.nolos.germantrainer.screens.TrainingScreens
import com.nolos.germantrainer.screens.vocab.VocabScreen
import com.nolos.germantrainer.speech.ISpeechProvider
import com.nolos.germantrainer.speech.SpeechProvider
import com.nolos.germantrainer.ui.theme.GermanTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var speech: ISpeechProvider

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GermanTrainerTheme {

                speech = SpeechProvider(androidx.compose.ui.platform.LocalContext.current)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "German Trainer")
                                },
                            )
                        },
                        bottomBar = {
                            GermanTrainerBottomBar(navController)
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            Navigation(navController = navController, speech = speech)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun GermanTrainerBottomBar(
    navController: NavHostController
) {
    NavigationBar() {
        NavScreens.values().forEach { navScreens ->

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val screenLabel = stringResource(id = navScreens.stringId)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = navScreens.icon,
                        contentDescription = screenLabel
                    )
                },
                label = { Text(screenLabel) },
                selected = currentRoute?.startsWith(navScreens.route) ?: false,
                colors = NavigationBarItemDefaults.colors(),
                onClick = {
                    navController.navigate(navScreens.route) {
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
                }
            )
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    speech: ISpeechProvider
) {
    NavHost(navController = navController, startDestination = NavScreens.HOME.route) {
        composable(route = NavScreens.HOME.route) {
            HomeScreen()
        }
        composable(route = NavScreens.TRAINING.route) {
            TrainingScreen(navController)
        }
        composable(route = NavScreens.GAMES.route) {
            GamesScreen()
        }
        composable(route = NavScreens.SETTINGS.route) {
            SettingsScreen()
        }
        composable(route = NavScreens.SETTINGS.route) {
            SettingsScreen()
        }
        composable(route = TrainingScreens.VOCAB.route) {
            VocabScreen(speech)
        }
        composable(route = TrainingScreens.CONJUGATION.route) {
            ConjugationScreen()
        }
    }
}


