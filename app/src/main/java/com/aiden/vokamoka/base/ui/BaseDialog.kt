package com.aiden.vokamoka.base.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aiden.vokamoka.base.bind.DataBindDialog

abstract class BaseDialog<D:ViewDataBinding>: DataBindDialog<D>() {

    // ViewModel Provider의 정의
    private var mDialogProvider: ViewModelProvider? = null;

    private var mApplicationProvider: ViewModelProvider? = null;

    // Fragment 수준의 Provider
    protected fun<T: ViewModel> getDialogScopeViewModel(modelClass: Class<T>):T{
        if(mDialogProvider == null){
            mDialogProvider = ViewModelProvider(this)
        }
        return mDialogProvider!![modelClass]
    }

    // Application 수준의 Provider
    protected fun<T: ViewModel> getApplicationScopeViewModel(
        modelClass: Class<T>
    ) : T {
        if(mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(requireActivity())
        }
        return mApplicationProvider!![modelClass]
    }

    // ViewModel 중 팩토리를 통해서 생성해야 할 경우 사용할 수 있도록 메서드 오버로딩
    protected fun<T:ViewModel> getApplicationScopeViewModel(
        modelClass: Class<T>,
        factory: ViewModelProvider.Factory
    ) : T {
        if(mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(requireActivity(), factory)
        }
        return mApplicationProvider!![modelClass]
    }
}
