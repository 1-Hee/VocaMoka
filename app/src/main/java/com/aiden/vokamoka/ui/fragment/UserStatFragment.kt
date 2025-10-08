package com.aiden.vokamoka.ui.fragment

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ItemClickListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.dto.StatInfo
import com.aiden.vokamoka.databinding.FragmentUserStatBinding
import com.aiden.vokamoka.ui.viewmodel.UserStatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStatFragment : BaseFragment<FragmentUserStatBinding>(),
    ViewClickListener, ItemClickListener<StatInfo> {

    private val TAG = this.javaClass.simpleName

    private val userStatViewModel: UserStatViewModel by viewModels()


    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_user_stat,
            BR.vm, userStatViewModel)
            .addBindingParam(BR.click, this)
            .addBindingParam(BR.itemClick, this)
    }

    override fun initViewModel() {
        val context = requireContext()
        val tempIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_statistics)
        this.userStatViewModel.setStatMenuList(
            listOf(
                StatInfo("암기 핵심 지표", iconDrawable = tempIcon, statResId = -1),
                StatInfo("개인 루틴 분석", iconDrawable = tempIcon, statResId = -1),
                StatInfo("전체 학습 인사이트", iconDrawable = tempIcon, statResId = -1),
                StatInfo("통계 요약", iconDrawable = tempIcon, statResId = -1),
            )
        )
    }

    override fun onStart() {
        super.onStart()
        showBackButton(true)
    }

    override fun initView() {

    }

    override fun onViewClick(view: View) {
        when(view.id){

            else -> {

            }
        }
    }

    override fun onItemClick(
        view: View,
        position: Int,
        item: StatInfo
    ) {
        Log.d(TAG, "onItemClick() ... ${item.toString()}")

        when(item.statResId) {
            else -> {

            }
        }
    }

    /**
     *  통계 데이터 아이디어
     *
     * #  단어 암기 핵심 지표
     *
     * 1) 일일 학습 단어 수
     * 하루 동안 학습한 총 단어의 개수 입니다.
     *
     * 2) 주간 누적 학습 단어
     * 한 주 동안 학습한 총 단어의 개수 입니다.
     *
     * 3) 월간 누적 학습 단어
     * 한 달 동안 학습한 총 단어의 개수 입니다.
     *
     * 4) 일일 평균 학습 단어 수 (월간)
     * 한 달 동안 학습한 총 단어를 실제 학습을 진행한 일자로 나눈 값입니다.
     *
     *
     * # 개인 루틴 분석
     *
     * 1) 요일별 학습 빈도 차트
     *
     * 2) 시간대별 학습 패턴
     * “주로 오전 9~10시, 점심 후 2시대 학습”
     *
     * 3) 세션당 평균 학습 단어 추이
     *
     *
     * # 전체 학습 인사이트
     *
     *
     *
     * “500단어 돌파 🎉”
     * “30일 연속 학습! 꾸준함은 최고의 습관입니다.”
     *
     *
     */
}