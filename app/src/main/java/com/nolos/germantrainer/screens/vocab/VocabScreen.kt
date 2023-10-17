package com.nolos.germantrainer.screens.vocab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nolos.germantrainer.R

@Composable
fun VocabScreen(vocabViewModel: VocabViewModel = viewModel()) {

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1.0f)) {
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
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
        }
        Column(modifier = Modifier.weight(4.0f)) {
            WordPanel(vocabViewModel)
        }
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
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(8.0f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = vocabViewModel.currentEnglishWord)
            Card(onClick = { vocabViewModel.revealWord() }) {
                if (vocabViewModel.germanWordShown) {
                    Text(text = "${vocabViewModel.currentGermanArticle} ${vocabViewModel.currentGermanWord}")
                } else if (vocabViewModel.currentGermanWord.isNotEmpty()) {
                    Text(text = stringResource(id = R.string.generic_word))
                }
            }
        }
        Column(
            modifier = Modifier.weight(1.0f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextButton(onClick = { vocabViewModel.nextWord() }, modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(id = R.string.next_word))
            }
        }
    }

}