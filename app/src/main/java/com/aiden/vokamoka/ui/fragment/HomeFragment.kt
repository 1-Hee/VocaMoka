package com.aiden.vokamoka.ui.fragment

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
        homeViewModel.loadUserInfo()

        homeViewModel.userInfoFlag.observe(this) { flag ->
            if(flag){
                // nav().navigate(R.id.welcomeFragment)
                nav().navigate(R.id.welcomeFragment)
                Log.d(this.javaClass.simpleName, "flag : $flag")
            }
        }


        // todo
        val msg1 = "[닉네임] 님\n오늘도 단어 암기를 시작해 볼까요?"
        val msg2 = "오늘 하루 n명의 Moka들이 단어 암기에 함께했어요!"
        val countMsg = "지금까지 [n]회 학습 하셨네요!"
        this.homeViewModel.setUserGreetMessage1(msg1)
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