package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaWord: ObservableField<String> = ObservableField()
    private val _vocaMeans: ObservableField<String> = ObservableField()
    private val _tagName: ObservableField<String> = ObservableField()


    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaWord: ObservableField<String> get() = _vocaWord
    val vocaMeans: ObservableField<String> get() = _vocaMeans
    val tagName: ObservableField<String> get() = _tagName

    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------

    fun setVocaWord(word : String) {
        this._vocaWord.set(word)
    }

    fun setVocaMeans(vocaMeans : String){
        this._vocaMeans.set(vocaMeans)
    }

    fun setTagName(tagName : String) {
        this._tagName.set(tagName)
    }


}