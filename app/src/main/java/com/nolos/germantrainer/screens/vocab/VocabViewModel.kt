package com.nolos.germantrainer.screens.vocab

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.nolos.germantrainer.speech.ISpeechProvider
import java.util.Locale
import kotlin.random.Random

class VocabViewModel(val speech: ISpeechProvider) : ViewModel() {

    var categoryFiltersExpanded by mutableStateOf(false)
        private set

    var categories: SnapshotStateList<String> = mutableStateListOf()
    var selectedCategories: SnapshotStateList<String> = mutableStateListOf()

    var typeFiltersExpanded by mutableStateOf(false)
        private set

    var types: SnapshotStateList<String> = mutableStateListOf("Noun", "Verb", "Adjective")
    var selectedTypes: SnapshotStateList<String> = mutableStateListOf("Noun", "Verb", "Adjective")


    var words: SnapshotStateList<IWord> = mutableStateListOf()

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

        // Future: See if we can make words all one list in the firebase JSON and deserialize
        // based on inferred or explicit Word sub-class
        val vocabNounsQuery = database.child("vocab").child("nouns")
        vocabNounsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                val nounsArrayList = snapshot.getValue<ArrayList<Noun>>()
                if (nounsArrayList != null) {
                    for (noun in nounsArrayList) {
                        words.add(noun)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VocabViewModel", error.toString())
            }

        })

        val vocabVerbsQuery = database.child("vocab").child("verbs")
        vocabVerbsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                val verbsArrayList = snapshot.getValue<ArrayList<Verb>>()
                if (verbsArrayList != null) {
                    for (verb in verbsArrayList) {
                        words.add(verb)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VocabViewModel", error.toString())
            }

        })

        val vocabAdjectivesQuery = database.child("vocab").child("adjectives")
        vocabAdjectivesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                val adjArrayList = snapshot.getValue<ArrayList<Adjective>>()
                if (adjArrayList != null) {
                    for (adj in adjArrayList) {
                        words.add(adj)
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

    fun toggleCategoryFilters() {
        categoryFiltersExpanded = !categoryFiltersExpanded
    }

    fun toggleTypeFilters() {
        typeFiltersExpanded = !typeFiltersExpanded
    }

    fun nextWord() {
        germanWordShown = false

        val filteredWords =
            words.filter { word -> word.categories.any { cat -> selectedCategories.contains(cat) } }
                .filter { word ->
                    selectedTypes.contains("Noun") && word is Noun
                            || selectedTypes.contains("Verb") && word is Verb
                            || selectedTypes.contains("Adjective") && word is Adjective
                }

        val currentWord =
            if (filteredWords.isNotEmpty()) filteredWords[Random.nextInt(filteredWords.size)] else null

        if (currentWord != null) {
            currentEnglishWord = currentWord.english
            currentGermanWord = currentWord.german
            currentGermanArticle = if (currentWord is Noun) currentWord.article else ""

        } else {
            currentEnglishWord = ""
            currentGermanArticle = ""
            currentGermanWord = ""
        }
    }

    fun revealWord() {
        germanWordShown = true
        speakWord()
    }

    fun speakWord() {
        speech.setLanguage(if (germanWordShown) Locale.GERMAN else Locale.US)
        speech.speak(if (germanWordShown) "$currentGermanArticle $currentGermanWord" else "the $currentEnglishWord")
    }

}