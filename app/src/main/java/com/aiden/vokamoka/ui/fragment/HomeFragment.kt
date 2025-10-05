package com.aiden.vokamoka.ui.fragment

import android.os.Handler
import android.os.Looper
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home)
    }

    override fun initViewModel() {

    }

    override fun onResume() {
        super.onResume()
        showBackButton(false)
    }

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            nav().navigate(R.id.vocaFragment)
        }, 300)

    }
}