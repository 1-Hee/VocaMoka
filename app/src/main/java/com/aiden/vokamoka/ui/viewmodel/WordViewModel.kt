package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiden.vokamoka.data.repository.ExpressionRepository
import com.aiden.vokamoka.data.repository.WordPoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WordViewModel @Inject constructor(
    private val poolRepo: WordPoolRepository,
    private val expRepo: ExpressionRepository,
) : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaWord: ObservableField<String> = ObservableField()
    private val _vocaMeans: ObservableField<String> = ObservableField()
    private val _tagName: ObservableField<String> = ObservableField()
    private val _wpIndex: ObservableField<Long> = ObservableField()


    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaWord: ObservableField<String> get() = _vocaWord
    val vocaMeans: ObservableField<String> get() = _vocaMeans
    val tagName: ObservableField<String> get() = _tagName
    val wpIndex: ObservableField<Long> get() = _wpIndex

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

    fun setWpIndex(index: Long){
        this._wpIndex.set(index)
    }

    fun deleteItem() {
        viewModelScope.launch(Dispatchers.IO) {
            val mWpIndex:Long = wpIndex.get() ?: -1
            if(mWpIndex < 0) return@launch
            poolRepo.deleteWordPool(mWpIndex)
        }
    }


}