package com.aiden.vokamoka.ui.viewmodel

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class CameraEditViewModel @Inject constructor() : ViewModel() {

    // * ---------------------------------
    // *    Variables
    // * ---------------------------------
    private val _isImgReady: ObservableField<Boolean> = ObservableField()
    private val _imgBitmap: ObservableField<Bitmap?> = ObservableField()


    // * ---------------------------------
    // *    Getter
    // * ---------------------------------
    val isImgReady: ObservableField<Boolean> get() = _isImgReady
    val imgBitmap: ObservableField<Bitmap?> get() = _imgBitmap

    // * ---------------------------------
    // *    Setter
    // * ---------------------------------

    fun setIsImgReady(flag : Boolean) {
        this._isImgReady.set(flag)
    }

    fun setImgBitmap(bitmap: Bitmap?){
        this._imgBitmap.set(bitmap)
    }

}