package com.aiden.vokamoka.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ItemClickListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.vo.MenuInfo
import com.aiden.vokamoka.databinding.FragmentEditWordBinding
import com.aiden.vokamoka.ui.viewmodel.EditWordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditWordFragment : BaseFragment<FragmentEditWordBinding>(),
    ViewClickListener, ItemClickListener<MenuInfo> {

    private val TAG = this.javaClass.simpleName
    private val editWordViewModel: EditWordViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_edit_word,
            BR.vm, editWordViewModel)
            .addBindingParam(BR.click, this)
            .addBindingParam(BR.itemClick, this)
    }

    override fun initViewModel() {
        val menuList:List<Pair<String, Int>> = listOf(
            Pair("단어 입력하기(Basic)", R.id.plainTextFragment),
            Pair("단어 입력하기(Camera)", R.id.cameraEditFragment),
            Pair("암기 일정 편집", R.id.wordScheduleFragment),
            Pair("단어 목록 편집", R.id.vocaEditFragment),
        )

        val menuInfoList: MutableList<MenuInfo> = mutableListOf()
        menuList.forEach { info ->
            val mInfo = MenuInfo(
                info.first,
                info.second
            )
            menuInfoList.add(mInfo)
        }
        editWordViewModel.setVocaMenuList(menuInfoList)
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
        item: MenuInfo
    ) {
        when(item.menuId) {
            R.id.plainTextFragment -> { // plain editor
                nav().navigate(item.menuId)
            }
            R.id.cameraEditFragment -> {
                nav().navigate(item.menuId)
            }
            R.id.wordScheduleFragment -> {
                nav().navigate(item.menuId)
            }
            R.id.vocaEditFragment -> {
                nav().navigate(item.menuId)
            }
            else -> {

            }
        }
    }
}