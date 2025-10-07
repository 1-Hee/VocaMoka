package com.aiden.vokamoka.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ItemClickListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.vo.DisplayWord
import com.aiden.vokamoka.databinding.FragmentWordBinding
import com.aiden.vokamoka.ui.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordFragment(
    private val displayWord: DisplayWord? = null
) : BaseFragment<FragmentWordBinding>(),
    ViewClickListener, ItemClickListener<DisplayWord> {

    private var isFrontVisible = true // 카드 애니메이션용
    private val index by lazy { arguments?.getInt(ARG_INDEX) ?: -1 } // 프래그먼트의 순서
    private val TAG = this.javaClass.simpleName
    private val wordViewModel: WordViewModel by viewModels()

    companion object {
        private const val ARG_INDEX = "index"
        fun newInstance(index: Int, displayWord: DisplayWord? = null) : WordFragment {
            val fragment = WordFragment(displayWord)
            fragment.arguments = Bundle().apply {
                putInt(ARG_INDEX, index)
            }
            return fragment
        }
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_word,
            BR.vm, wordViewModel)
    }

    override fun initViewModel() {
        // wordViewModel = getFragmentScopeViewModel(WordViewModel::class.java)
        displayWord?:return
        wordViewModel.setVocaWord(displayWord.word)
        wordViewModel.setVocaMeans(displayWord.mean)
        wordViewModel.setTagName(displayWord.tag)
    }

    override fun initView() {
        mBinding.clVocaWord.setOnClickListener { v ->
            displayWord?:return@setOnClickListener
            onItemClick(v, index, displayWord)
            onViewClick(v)
        }
    }

    override fun onViewClick(view: View) {
        when(view.id){
            R.id.cl_voca_word -> {
                flipCard3D(
                    if (isFrontVisible) mBinding.mcvWordFront else mBinding.mcvWordBack,
                    if (isFrontVisible) mBinding.mcvWordBack else mBinding.mcvWordFront
                )
                isFrontVisible = !isFrontVisible
            }
            R.id.btn_check -> {
                Toast.makeText(requireContext(), "CHECK!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(
        view: View,
        position: Int,
        item: DisplayWord
    ) {
        Log.d(TAG, "[${position}] item : $displayWord ")
        // flipCard(mBinding.layoutFront, mBinding.layoutBack)
    }

    /**
     * 3D 카드 플립 애니메이션
     */
    private fun flipCard3D(front: View, back: View) {
        val scale = requireContext().resources.displayMetrics.density
        val distance = 8000 * scale
        front.cameraDistance = distance
        back.cameraDistance = distance

        // --- 앞면 회전 (0 → 90도)
        val flipOut = ObjectAnimator.ofFloat(front, "rotationY", 0f, 90f).apply {
            duration = 250
            interpolator = AccelerateInterpolator()
        }

        // --- 뒷면 회전 (-90 → 0도)
        val flipIn = ObjectAnimator.ofFloat(back, "rotationY", -90f, 0f).apply {
            duration = 250
            interpolator = DecelerateInterpolator()
        }

        // 중간 지점에서 면 전환
        flipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                front.visibility = View.GONE
                back.visibility = View.VISIBLE

                // 그림자(또는 elevation) 반전 효과
                back.elevation = front.elevation
                flipIn.start()
            }
        })

        // --- 살짝 스케일로 깊이감 추가 (optional)
        val shrink = ObjectAnimator.ofFloat(front, "scaleX", 1f, 0.95f).setDuration(150)
        val expand = ObjectAnimator.ofFloat(back, "scaleX", 0.95f, 1f).setDuration(150)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(
            AnimatorSet().apply { playTogether(flipOut, shrink) },
            AnimatorSet().apply { playTogether(flipIn, expand) }
        )
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }

    private fun flipCard(front: View, back: View) {
        val scale = requireContext().resources.displayMetrics.density
        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        val flipOut = ObjectAnimator.ofFloat(front, "rotationY", 0f, 90f)
        val flipIn = ObjectAnimator.ofFloat(back, "rotationY", -90f, 0f)

        flipOut.duration = 300
        flipIn.duration = 300

        flipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                front.visibility = View.GONE
                back.visibility = View.VISIBLE
                flipIn.start()
            }
        })

        flipOut.start()
    }
}