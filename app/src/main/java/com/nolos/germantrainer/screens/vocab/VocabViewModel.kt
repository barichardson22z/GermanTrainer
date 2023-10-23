package com.nolos.germantrainer.screens.vocab

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.nolos.germantrainer.R
import com.nolos.germantrainer.speech.ISpeechProvider
import java.util.Locale
import kotlin.random.Random

class VocabViewModel(val speech: ISpeechProvider) : ViewModel() {

    var filtersExpanded by mutableStateOf(false)
        private set

    var categories: SnapshotStateList<String> = mutableStateListOf()
    var selectedCategories: SnapshotStateList<String> = mutableStateListOf()
    var nouns: SnapshotStateList<Noun> = mutableStateListOf()

    var currentEnglishWord by mutableStateOf("")
        private set
    var germanWordShown by mutableStateOf(false)
        private set
    var currentGermanArticle by mutableStateOf("")
        private set
    var currentGermanWord by mutableStateOf("")
        private set


    init {
        val database = Firebase.database.reference
        val vocabCategoriesQuery = database.child("vocab").child("categories").orderByKey()

        vocabCategoriesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                val categoriesArrayList = snapshot.getValue<ArrayList<String>>()
                if (categoriesArrayList != null) {
                    for (category in categoriesArrayList) {
                        categories.add(category)
                    }
                }
                selectedCategories.addAll(categories)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VocabViewModel", error.toString())
            }

        })

        val vocabNounsQuery = database.child("vocab").child("nouns")
        vocabNounsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                val nounsArrayList = snapshot.getValue<ArrayList<Noun>>()
                if (nounsArrayList != null) {
                    for (noun in nounsArrayList) {
                        nouns.add(noun)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VocabViewModel", error.toString())
            }

        })
    }

    class Factory(private val speechProvider: ISpeechProvider) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VocabViewModel(speechProvider) as T
        }
    }

    fun toggleFilters() {
        filtersExpanded = !filtersExpanded
    }

    fun nextWord() {
        germanWordShown = false

        var filteredWords =
            nouns.filter { noun -> noun.categories.any { cat -> selectedCategories.contains(cat) } }

        val currentWord =
            if (filteredWords.isNotEmpty()) filteredWords[Random.nextInt(filteredWords.size)] else null

        if (currentWord != null) {
            currentEnglishWord = currentWord.english
            currentGermanArticle = currentWord.article
            currentGermanWord = currentWord.german
        } else {
            currentEnglishWord = ""
            currentGermanArticle = ""
            currentGermanWord = ""
        }
    }

    fun revealWord() {
        germanWordShown = true
    }

    fun speakWord() {
        speech.setLanguage(if (germanWordShown) Locale.GERMAN else Locale.US)
        speech.speak(if (germanWordShown) "$currentGermanArticle $currentGermanWord" else "the $currentEnglishWord")
    }

}