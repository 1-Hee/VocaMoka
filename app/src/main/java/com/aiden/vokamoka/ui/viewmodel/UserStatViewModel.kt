package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.aiden.vokamoka.data.dto.StatInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UserStatViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _statMenuList: ObservableArrayList<StatInfo> = ObservableArrayList()

    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val statMenuList: ObservableArrayList<StatInfo> get() = _statMenuList

    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------
    fun setStatMenuList(statMenuList:List<StatInfo>) {
        this._statMenuList.clear()
        this._statMenuList.addAll(statMenuList)
    }

}