package com.nolos.germantrainer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VocabScreen(vocabViewModel: VocabViewModel = viewModel()) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Categories:")
        LazyColumn {
            vocabViewModel.categories.forEach { category ->
                item {
                    Button(modifier = Modifier.fillMaxSize(),
                        onClick = {
                            Toast.makeText(
                                context,
                                "Clicked category: $category",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                        Text(text = category)
                    }
                }
            }
        }
    }

}