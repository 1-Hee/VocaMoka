package com.aiden.vokamoka.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.aiden.vokamoka.data.vo.MenuInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class VocaLoadViewModel @Inject constructor() : ViewModel() {


    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _vocaMenuList: ObservableArrayList<MenuInfo> = ObservableArrayList()

    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val vocaMenuList: ObservableArrayList<MenuInfo> get() = _vocaMenuList

    // * ---------------------------
    // *   ViewModel Setter
    // * ---------------------------
    fun setVocaMenuList(vocaMenuList:List<MenuInfo>) {
        this._vocaMenuList.clear()
        this._vocaMenuList.addAll(vocaMenuList)
    }

}