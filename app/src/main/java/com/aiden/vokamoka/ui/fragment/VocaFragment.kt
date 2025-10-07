package com.aiden.vokamoka.ui.fragment

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.OnPageInfoListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.vo.DisplayWord
import com.aiden.vokamoka.data.vo.MenuInfo
import com.aiden.vokamoka.databinding.FragmentVocaBinding
import com.aiden.vokamoka.ui.viewmodel.VocaViewModel
import com.aiden.vokamoka.util.AppUtil.parcelable

class VocaFragment : BaseFragment<FragmentVocaBinding>(),
    ViewClickListener, OnPageInfoListener {

    private val TAG = this.javaClass.simpleName
    private lateinit var vocaViewModel: VocaViewModel

    private val AUTO_SCROLL_INTERVAL = 10 // n초 단위
    private var countdown = AUTO_SCROLL_INTERVAL
    private val isStartTimer: Boolean = true

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_voca,
            BR.vm, vocaViewModel)
            .addBindingParam(BR.onPageListener, this)
            .addBindingParam(BR.click, this)
    }

    override fun initViewModel() {
        vocaViewModel = getFragmentScopeViewModel(VocaViewModel::class.java)
    }

    private val countdownHandler = Handler(Looper.getMainLooper())
    private val countdownRunnable = object : Runnable {
        override fun run() {
            // UI 업데이트
            vocaViewModel.setPageTimeValue(--countdown)
            mBinding.notifyChange()

            if (countdown < 0) {
                val adapter = mBinding.vpVocaWord.adapter?:return
                val viewPager2: ViewPager2 = mBinding.vpVocaWord
                // 다음 페이지로 이동
                val itemCount = adapter.itemCount
                val next = (viewPager2.currentItem + 1) % itemCount
                viewPager2.setCurrentItem(next, true)

                val position = next
                val realPosition = when (position) {
                    0 -> itemCount - 3     // 0번째(왼쪽 끝) → 마지막 실제 인덱스
                    itemCount - 1 -> 0     // 마지막(오른쪽 끝) → 첫 번째 실제 인덱스
                    else -> position - 1           // 나머지는 1 빼기
                }
                onPageChanged(realPosition, itemCount - 2)
                // 초기화
                countdown = AUTO_SCROLL_INTERVAL
                countdownHandler.removeCallbacks(this)
                vocaViewModel.setPageTimeValue(countdown)
                mBinding.notifyChange()
            }

            // 1초마다 반복
            countdownHandler.postDelayed(this, 1000)
        }
    }

    fun startAutoScroll() {
        if(!isStartTimer) return
        countdown = AUTO_SCROLL_INTERVAL
        countdownHandler.removeCallbacks(countdownRunnable)
        countdownHandler.post(countdownRunnable)
    }

    fun stopAutoScroll() {
        if(!isStartTimer) return
        countdown = AUTO_SCROLL_INTERVAL
        vocaViewModel.setPageTimeValue(countdown)
        mBinding.notifyChange()
        countdownHandler.removeCallbacks(countdownRunnable)
    }

    override fun initView() {
        showBackButton(true)
        Log.i(TAG, "initView()....")

        arguments.let { it ->
            val mMenuInfo : MenuInfo? = it?.parcelable("menuInfo")
            Log.d(TAG, "mMenuInfo : $mMenuInfo")
        }

        val temp: MutableList<DisplayWord> = mutableListOf()
        for(i in 0 until 10){
            val tempWord = DisplayWord(
                "Hello, World ${(i+1)}",
                "안녕, 코틀린!",
                "beginner"
            )
            temp.add(tempWord)
        }
        vocaViewModel.setVocaInfoList(temp)
        vocaViewModel.setPageTimeValue((AUTO_SCROLL_INTERVAL))
        mBinding.notifyChange()
    }

    override fun onPageChanged(current: Int, total: Int) {
        Log.d(TAG, "onPageChanged() ... $current / $total ")
        this.vocaViewModel.setPageCurrent(current+1)
        this.vocaViewModel.setPageTotal(total)
        stopAutoScroll()
        startAutoScroll()
        mBinding.notifyChange()
    }

    override fun onViewClick(view: View) {
        when(view.id) {
            R.id.btn_shuffle -> {

            }
            R.id.btn_goto_first -> {
                mBinding.vpVocaWord.setCurrentItem(1, true)
                mBinding.notifyChange()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        countdownHandler.removeCallbacks(countdownRunnable)
        stopAutoScroll()
    }
}