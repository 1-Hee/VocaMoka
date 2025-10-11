package com.aiden.vokamoka.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiden.vokamoka.R
import com.aiden.vokamoka.data.model.UserInfo
import com.aiden.vokamoka.data.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userInfoRepo:UserInfoRepository
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    // * ---------------------------
    // *   ViewModel Variables
    // * ---------------------------
    private val _userGreetMessage1: ObservableField<String> = ObservableField("")
    private val _userGreetMessage2: ObservableField<String> = ObservableField("")
    private val _userPlayCountMsg: ObservableField<String> = ObservableField("")

    private val _userInfoFlag: MutableLiveData<Boolean> = MutableLiveData()


    // * ---------------------------
    // *    ViewModel Getter
    // * ---------------------------
    val userGreetMessage1: ObservableField<String> get() = _userGreetMessage1
    val userGreetMessage2: ObservableField<String> get() = _userGreetMessage2
    val userPlayCountMsg: ObservableField<String> get() = _userPlayCountMsg

    val userInfoFlag: LiveData<Boolean> get() = _userInfoFlag


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

    fun setUserInfoFlag(flag : Boolean){
        this._userInfoFlag.postValue(flag)
    }

    fun loadUserInfo(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId: Long = userInfoRepo.readLastEntityId()
            val routeFlag: Boolean = (userId < 0)
            withContext(Dispatchers.Main){
                setUserInfoFlag(routeFlag)
            }

            if(routeFlag) return@launch
            val userInfo:UserInfo = userInfoRepo.readEntity(userId)
            val nickname: String = userInfo.userNickname
            val msg1: String = context.getString(R.string.msg_greet1, nickname)
            setUserGreetMessage1(msg1)

        }
    }

}