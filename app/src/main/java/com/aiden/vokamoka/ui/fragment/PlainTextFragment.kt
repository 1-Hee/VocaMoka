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

    override fun initView() {
    }

    override fun onViewClick(view: View) {
        when(view.id){
            else -> {

            }
        }
    }
}