package com.aiden.vokamoka.ui.fragment

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentHomeBinding
import com.aiden.vokamoka.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ViewClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home,
            BR.vm, homeViewModel)
            .addBindingParam(BR.click, this)
    }

    override fun initViewModel() { }

    override fun onStart() {
        super.onStart()
        showBackButton(false)
    }

    override fun initView() {
        val context: Context = requireContext()
        homeViewModel.loadUserInfo(context)

        homeViewModel.userInfoFlag.observe(this) { flag ->
            if(flag){
                nav().navigate(R.id.welcomeFragment)
            }
        }

        // todo
        val test2 = 1
        val test3 = 0
        val msg2 = getString(R.string.msg_greet2, test2)
        val countMsg = getString(R.string.msg_greet3, test3)
        this.homeViewModel.setUserGreetMessage2(msg2)
        this.homeViewModel.setUserPlayCountMsg(countMsg)
    }

    override fun onViewClick(view: View) {

        when(view.id){
            R.id.mcv_goto_voca -> { // 단어 암기
                nav().navigate(R.id.vocaLoadFragment)
            }
            R.id.mcv_goto_stat -> { // move 통계
                nav().navigate(R.id.userStatFragment)
            }
            R.id.mcv_goto_regist -> { // move edit
                nav().navigate(R.id.editWordFragment)

            }
        }
    }
}