package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class VocaViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaFragments: ObservableArrayList<Fragment> = ObservableArrayList<Fragment>()
    private val _pageCurrent: ObservableField<Int> = ObservableField(0)
    private val _pageTotal: ObservableField<Int> = ObservableField(0)
    private val _pageTimeValue: ObservableField<Int> = ObservableField(0)

    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaFragments: ObservableArrayList<Fragment> get() = _vocaFragments
    val pageCurrent: ObservableField<Int> get() =_pageCurrent
    val pageTotal: ObservableField<Int> get() = _pageTotal
    val pageTimeValue: ObservableField<Int> get() = _pageTimeValue

    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------

    fun setVocaFragments(fragments : List<Fragment>) {
        this._vocaFragments.clear()
        this._vocaFragments.addAll(fragments)
    }

    fun addVocaFragments(fragments : Fragment) {
        this._vocaFragments.add(fragments)
    }

    fun setPageCurrent(current : Int) {
        this._pageCurrent.set(current)
    }

    fun setPageTotal(total : Int) {
        this._pageTotal.set(total)
    }

    fun setPageTimeValue(timeValue : Int) {
        this._pageTimeValue.set(timeValue)
    }

}