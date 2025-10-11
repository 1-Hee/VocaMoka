package com.aiden.vokamoka.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiden.vokamoka.data.model.UserInfo
import com.aiden.vokamoka.data.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userInfoRepo:UserInfoRepository
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    // * ---------------------------------
    // *    Variables
    // * ---------------------------------
    private val _isUserRegistered: MutableLiveData<Boolean> = MutableLiveData()
    private val _isNickNameValid: ObservableField<Boolean> = ObservableField(false)

    // * ---------------------------------
    // *    Getter
    // * ---------------------------------
    val isUserRegistered: MutableLiveData<Boolean> get() = _isUserRegistered
    val isNickNameValid: ObservableField<Boolean> get() = _isNickNameValid

    // * ---------------------------------
    // *    Setter
    // * ---------------------------------
    fun setIsUserRegistered(flag : Boolean) {
        this._isUserRegistered.postValue(flag)
    }

    fun setIsNickNameValid(flag : Boolean){
        this._isNickNameValid.set(flag)
    }


    /**
     *  associate with database...
     */

    fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoList:List<UserInfo> = userInfoRepo.readEntityList()
            userInfoList.forEach { info ->
                Log.d(TAG, "USER_INFO : $info")
            }
            val flag: Boolean = userInfoList.isNotEmpty()
            withContext(Dispatchers.Main){
                setIsUserRegistered(flag)
            }

        }
    }

    fun addUserInfo(nickName: String, identifier: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mNewUserInfo = UserInfo(
                userNickname = nickName,
                userIndicator = identifier
            )
            Log.d(TAG, "User Info = $mNewUserInfo")
            userInfoRepo.addEntity(mNewUserInfo)
            loadUserInfo()
        }
    }

}