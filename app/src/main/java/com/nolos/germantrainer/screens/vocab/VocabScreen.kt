package com.nolos.germantrainer.screens.vocab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nolos.germantrainer.R
import com.nolos.germantrainer.speech.ISpeechProvider

@Composable
fun VocabScreen(
    speech: ISpeechProvider,
    vocabViewModel: VocabViewModel = viewModel(factory = VocabViewModel.Factory(speech))
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.categories))
            IconButton(onClick = { vocabViewModel.toggleFilters() }) {
                Icon(
                    if (vocabViewModel.filtersExpanded) Icons.Default.FilterListOff else Icons.Default.FilterList,
                    contentDescription = stringResource(id = R.string.show_filters)
                )
            }
        }

        AnimatedVisibility(visible = vocabViewModel.filtersExpanded) {
            FilterOptions(vocabViewModel)
        }

        WordPanel(vocabViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterOptions(vocabViewModel: VocabViewModel) {
    FlowRow(modifier = Modifier.padding(8.dp)) {
        vocabViewModel.categories.forEach { category ->
            FilterChip(
                selected = vocabViewModel.selectedCategories.contains(category),
                onClick = {
                    if (vocabViewModel.selectedCategories.contains(category))
                        vocabViewModel.selectedCategories.remove(category)
                    else
                        vocabViewModel.selectedCategories.add(category)

                },
                label = {
                    Text(text = category)
                })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordPanel(vocabViewModel: VocabViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .border(
                border = BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.inversePrimary
                )
            )
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        if (vocabViewModel.currentGermanWord.isNotEmpty() && vocabViewModel.currentEnglishWord.isNotEmpty()) {
            Card(
                onClick = { vocabViewModel.revealWord() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    if (vocabViewModel.germanWordShown) {
                        if (vocabViewModel.currentGermanArticle.isNotEmpty()) {
                            Text(text = "${vocabViewModel.currentGermanArticle} ", fontSize = 25.sp)
                        }
                        Text(text = "${vocabViewModel.currentGermanWord}", fontSize = 25.sp)
                    } else {
                        if (vocabViewModel.currentGermanArticle.isNotEmpty()) {
                            Text(
                                text = "the ",
                                fontSize = 25.sp
                            )
                        }
                        Text(text = "${vocabViewModel.currentEnglishWord}", fontSize = 25.sp)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { vocabViewModel.revealWord() }
                ) {
                    Icon(
                        Icons.Default.RemoveRedEye,
                        contentDescription = stringResource(id = R.string.reveal_word)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    onClick = { vocabViewModel.speakWord() }
                ) {
                    Icon(
                        Icons.Default.VolumeUp,
                        contentDescription = stringResource(id = R.string.listen_word)
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextButton(onClick = { vocabViewModel.nextWord() }) {
                Text(text = stringResource(id = R.string.next_word), fontSize = 35.sp)
            }
        }
    }

}