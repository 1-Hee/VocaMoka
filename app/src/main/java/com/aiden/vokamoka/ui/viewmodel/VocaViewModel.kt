package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.aiden.vokamoka.data.vo.DisplayWord
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class VocaViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaInfoList: ObservableArrayList<DisplayWord> = ObservableArrayList()
    private val _pageCurrent: ObservableField<Int> = ObservableField(0)
    private val _pageTotal: ObservableField<Int> = ObservableField(0)
    private val _pageTimeValue: ObservableField<Int> = ObservableField(0)

    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaInfoList: ObservableArrayList<DisplayWord> get() = _vocaInfoList
    val pageCurrent: ObservableField<Int> get() =_pageCurrent
    val pageTotal: ObservableField<Int> get() = _pageTotal
    val pageTimeValue: ObservableField<Int> get() = _pageTimeValue

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

}