package com.aiden.vokamoka.ui.fragment

import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentVocaBinding

class VocaFragment : BaseFragment<FragmentVocaBinding>() {

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_voca)
    }

    override fun initViewModel() {

    }

    override fun initView() {
        showBackButton(true)
    }
}