package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _userGreetMessage1: ObservableField<String> = ObservableField("")
    private val _userGreetMessage2: ObservableField<String> = ObservableField("")
    private val _userPlayCountMsg: ObservableField<String> = ObservableField("")


    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val userGreetMessage1: ObservableField<String> get() = _userGreetMessage1
    val userGreetMessage2: ObservableField<String> get() = _userGreetMessage2
    val userPlayCountMsg: ObservableField<String> get() = _userPlayCountMsg


    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------
    fun setUserGreetMessage1(msg:String) {
        this._userGreetMessage1.set(msg)
    }

    fun setUserGreetMessage2(msg:String) {
        this._userGreetMessage2.set(msg)
    }

    fun setUserPlayCountMsg(msg:String) {
        this._userPlayCountMsg.set(msg)
    }

}