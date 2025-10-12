package com.aiden.vokamoka.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiden.vokamoka.data.model.Expression
import com.aiden.vokamoka.data.model.WordPool
import com.aiden.vokamoka.data.repository.ExpressionRepository
import com.aiden.vokamoka.data.repository.WordPoolRepository
import com.aiden.vokamoka.data.vo.DisplayWord
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

@HiltViewModel
class VocaViewModel @Inject constructor(
    private val expRepo: ExpressionRepository,
    private val poolRepo: WordPoolRepository,
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaInfoList: ObservableArrayList<DisplayWord> = ObservableArrayList()
    private val _pageCurrent: ObservableField<Int> = ObservableField(0)
    private val _pageTotal: ObservableField<Int> = ObservableField(0)
    private val _pageTimeValue: ObservableField<Int> = ObservableField(0)
    private val _isVocaUpdated: MutableLiveData<Boolean> = MutableLiveData()

    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaInfoList: ObservableArrayList<DisplayWord> get() = _vocaInfoList
    val pageCurrent: ObservableField<Int> get() =_pageCurrent
    val pageTotal: ObservableField<Int> get() = _pageTotal
    val pageTimeValue: ObservableField<Int> get() = _pageTimeValue
    val isVocaUpdated: LiveData<Boolean> get() = _isVocaUpdated

    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------

    fun setPageCurrent(current : Int) {
        this._pageCurrent.set(current)
    }

    fun addVocaInfoList(displayWord: DisplayWord){
        this._vocaInfoList.add(displayWord)
    }

    fun setVocaInfoList(displayWords : List<DisplayWord>){
        this._vocaInfoList.clear()
        this._vocaInfoList.addAll(displayWords)
    }

    fun setPageTotal(total : Int) {
        this._pageTotal.set(total)
    }

    fun setPageTimeValue(timeValue : Int) {
        this._pageTimeValue.set(timeValue)
    }

    fun setIsVocaUpdated(flag : Boolean){
        this._isVocaUpdated.postValue(flag)
    }

    //
    fun loadVocaList(){
        viewModelScope.launch(Dispatchers.IO) {
            val mDisplayWords: MutableList<DisplayWord> = mutableListOf()
            val mPoolList:List<WordPool> = poolRepo.readEntityList()
            mPoolList.forEachIndexed { i, pool ->
                Log.d(TAG, "POOL $pool")
                val expFrom: Expression = expRepo.readEntity(pool.originExpId)
                val expTo: Expression = expRepo.readEntity(pool.targetExpId)

                val mWord = DisplayWord(
                    expFrom.wordText,
                    expTo.wordText,
                    "None",
                    pool.wpInex,
                    i
                )
                mDisplayWords.add(mWord)
            }

            withContext(Dispatchers.Main) {
                setVocaInfoList(mDisplayWords)
                setIsVocaUpdated(true)
            }
        }
    }

    // temp..!
    fun deleteAllVoca() {
        viewModelScope.launch(Dispatchers.IO) {

            poolRepo.removeAll()
            expRepo.removeAll()

            withContext(Dispatchers.Main) {
                setVocaInfoList(emptyList())
                setIsVocaUpdated(true)
            }
        }
    }

}