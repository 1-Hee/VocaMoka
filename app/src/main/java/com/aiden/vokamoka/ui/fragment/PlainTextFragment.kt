package com.aiden.vokamoka.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentPlainTextBinding
import com.aiden.vokamoka.ui.viewmodel.PlainTextViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlainTextFragment : BaseFragment<FragmentPlainTextBinding>(), ViewClickListener {

    private val plainTextViewModel: PlainTextViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_plain_text,
            BR.vm, plainTextViewModel)
            .addBindingParam(BR.click, this)
    }

    override fun initViewModel() {
    }

    private fun showSnackBar(msg: String){
        Snackbar
            .make(requireView(), msg, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun initView() {
        plainTextViewModel.isAddWord.observe(this) { flag ->
            if(flag){
                val msg: String = getString(R.string.msg_complete_word)
                showSnackBar(msg)
            }

        }
    }

    override fun onViewClick(view: View) {
        when(view.id){
            R.id.btn_load_data -> { // 불러오기
                // TODO...

            }
            R.id.btn_reset_data -> { // 초기화
                mBinding.etPlainTextEditor.text?.clear()
                mBinding.notifyChange()
            }
            R.id.btn_save_word -> { // 저장하기
                val editTextValue: String = mBinding.etPlainTextEditor.text.toString()
                plainTextViewModel.addTextTokens(editTextValue)

            }
            else -> {
                // Apple / 사과 / 명사

            }
        }
    }
}