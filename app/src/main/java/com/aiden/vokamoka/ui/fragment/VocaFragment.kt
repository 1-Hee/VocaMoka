package com.aiden.vokamoka.ui.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.OnPageInfoListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.vo.DisplayWord
import com.aiden.vokamoka.databinding.FragmentVocaBinding
import com.aiden.vokamoka.ui.viewmodel.VocaViewModel

class VocaFragment : BaseFragment<FragmentVocaBinding>(), OnPageInfoListener {

    private val TAG = this.javaClass.simpleName
    private lateinit var vocaViewModel: VocaViewModel

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_voca,
            BR.vm, vocaViewModel)
            .addBindingParam(BR.onPageListener, this)
    }

    override fun initViewModel() {
        vocaViewModel = getFragmentScopeViewModel(VocaViewModel::class.java)
    }

    override fun initView() {
        showBackButton(true)
        Log.i(TAG, "initView()....")

        val temp: MutableList<Fragment> = mutableListOf()
        for(i in 0 until 10){
            val tempWord = DisplayWord(
                "Hello, World ${(i+1)}",
                "안녕, 코틀린!",
                "beginner"
            )
            val mFragment = WordFragment(tempWord)
            mFragment.setPosition(i)
            temp.add(
                mFragment
            )
        }
        vocaViewModel.setVocaFragments(temp)
    }

    override fun onPageChanged(current: Int, total: Int) {
        Log.d(TAG, "onPageChanged() ... $current / $total ")
        this.vocaViewModel.setPageCurrent(current+1)
        this.vocaViewModel.setPageTotal(total)
        mBinding.notifyChange()
    }
}