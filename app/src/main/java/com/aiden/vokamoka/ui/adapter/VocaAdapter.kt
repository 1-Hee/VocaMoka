package com.aiden.vokamoka.ui.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aiden.vokamoka.data.vo.DisplayWord
import com.aiden.vokamoka.ui.fragment.WordFragment
import com.aiden.vokamoka.ui.fragment.WordFragment.OnWordListener

class VocaAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private var onWordListener : OnWordListener? = null

    fun setOnWordListener(listener: OnWordListener?){
        this.onWordListener = listener
    }

    private val _displayWordList: MutableList<DisplayWord> = mutableListOf()

    val displayWordList:List<DisplayWord> get() = _displayWordList

    fun addDisplayWord(displayWord: DisplayWord) {
        this._displayWordList.add(displayWord)
        this.notifyItemChanged(_displayWordList.size-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDisplayWord(displayWordList:List<DisplayWord>) {
        this._displayWordList.clear()
        this._displayWordList.addAll(displayWordList)
        notifyDataSetChanged()
    }
    override fun getItemCount() = if(_displayWordList.isEmpty()) 0 else (_displayWordList.size + 2)

    override fun createFragment(position: Int): Fragment {
        if(_displayWordList.isEmpty()) return Fragment()

        val itemSize:Int = this._displayWordList.size
//        val realPosition = when (position) {
//            0 -> itemSize - 1 // 마지막 아이템 복제
//            itemSize + 1 -> 0 // 첫 아이템 복제
//            else -> position - 1
//        }
        val realPosition = position

        // 실제 데이터에 따라 Fragment를 생성
        val instance =  WordFragment
            .newInstance(realPosition, _displayWordList[realPosition])
        instance.setOnWordListener(onWordListener)
        return instance
    }
}
