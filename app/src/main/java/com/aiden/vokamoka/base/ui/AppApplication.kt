package com.aiden.vokamoka.base.ui

import android.util.Log
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.aiden.vokamoka.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication(
    private val mAppViewModelStore: ViewModelStore = ViewModelStore()
) : MultiDexApplication(), ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            // TODO
        }
    }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore
}