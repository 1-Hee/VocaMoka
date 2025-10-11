package com.aiden.vokamoka.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.R
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentWelcomeBinding
import com.aiden.vokamoka.ui.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(), ViewClickListener {

    private val TAG = this.javaClass.simpleName
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
       return DataBindingConfig(R.layout.fragment_welcome,
           BR.vm, welcomeViewModel)
           .addBindingParam(BR.click, this)
    }

    override fun initViewModel() { }

    override fun initView() {
        welcomeViewModel.loadUserInfo()

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 실시간으로 바뀔 때 (매 타이핑)
            }
            override fun afterTextChanged(s: Editable?) {
                // 수정이 끝난 직후 (이곳에서 validation/format 권장)
                val flag: Boolean = s?.toString()?.isNotEmpty() ?: false
                welcomeViewModel.setIsNickNameValid(flag)
            }
        }

        mBinding.etUserNickname.addTextChangedListener(watcher)

    }

    override fun onViewClick(view: View) {
        when(view.id) {
            R.id.btn_voka_start -> {


            }
            else -> {


            }
        }
    }
}