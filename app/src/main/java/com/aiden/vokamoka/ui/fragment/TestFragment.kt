package com.aiden.vokamoka.ui.fragment

import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentTestBinding
import com.aiden.vokamoka.ui.adapter.VocaAdapter


class TestFragment : BaseFragment<FragmentTestBinding>() {

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_test)
    }

    override fun initViewModel() {

    }

    override fun initView() {

    }
}