package com.aiden.vokamoka.ui.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VocaAdapter (
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val _fragments: MutableList<Fragment> = mutableListOf()

    fun addFragment(fragment: Fragment) {
        this._fragments.add(fragment)
        this.notifyItemChanged(_fragments.size-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFragment(fragList:List<Fragment>) {
        this._fragments.clear()
        this._fragments.addAll(fragList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _fragments.size

    override fun createFragment(position: Int) = _fragments[position]
}
