package com.nolos.germantrainer.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class VocabViewModel : ViewModel() {
    var categories: SnapshotStateList<String> = mutableStateListOf()

    //private var currentWord = mutableStateOf("")

    init {
        val database = Firebase.database.reference
        val vocabCategoriesQuery = database.child("vocab").child("categories").orderByKey()

        vocabCategoriesQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                @Suppress("UNCHECKED_CAST")
                categories.addAll((snapshot.value as Map<String, String>).values)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VocabViewModel", error.toString())
            }

        })
    }
}