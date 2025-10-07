package com.aiden.vokamoka.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ItemClickListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.vo.MenuInfo
import com.aiden.vokamoka.databinding.FragmentVocaLoadBinding
import com.aiden.vokamoka.ui.viewmodel.VocaLoadViewModel
import com.aiden.vokamoka.util.AppUtil.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VocaLoadFragment : BaseFragment<FragmentVocaLoadBinding>(),
    ViewClickListener, ItemClickListener<MenuInfo> {

    private val TAG = this.javaClass.simpleName
    private val vocaLoadViewModel: VocaLoadViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_voca_load,
            BR.vm, vocaLoadViewModel)
            .addBindingParam(BR.click, this)
            .addBindingParam(BR.itemClick, this)
    }

    override fun initViewModel() {
        val menuList = listOf<String>(
            "빠른 시작하기",
            "파일 불러오기",
            "데이터베이스 불러오기"
        )
        val menuInfoList: MutableList<MenuInfo> = mutableListOf()
        menuList.forEach { name ->
            val mInfo = MenuInfo(
                name, -1
            )
            menuInfoList.add(mInfo)
        }
        vocaLoadViewModel.setVocaMenuList(menuInfoList)
    }

    override fun initView() {
        showBackButton(true)
    }

    override fun onViewClick(view: View) {

    }

    override fun onItemClick(
        view: View,
        position: Int,
        item: MenuInfo
    ) {
        Log.d(TAG, "[$position] ITEM INFO $item")
        val bundle = Bundle()
        bundle.putParcelable("menuInfo", item)
        nav().navigate(R.id.vocaFragment, bundle)
     }
}