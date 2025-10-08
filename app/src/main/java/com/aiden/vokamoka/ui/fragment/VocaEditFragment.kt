package com.aiden.vokamoka.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentVocaEditBinding
import com.aiden.vokamoka.ui.viewmodel.VocaEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VocaEditFragment : BaseFragment<FragmentVocaEditBinding>(), ViewClickListener {

    private val vocaEditViewModel: VocaEditViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_voca_edit,
            BR.vm, vocaEditViewModel
        ).addBindingParam(BR.click, this)
    }

    override fun initViewModel() { }

    override fun initView() {

    }

    override fun onViewClick(view: View) {
        when(view.id){
            else -> {

            }
        }
    }
}