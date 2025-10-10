package com.aiden.vokamoka.ui.fragment

import android.content.Context
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
        val context: Context = requireContext()
        val menuArray:Array<String> = context.resources
            .getStringArray(R.array.arr_menu_edit_word); //  Edit Word Menu
        val menuInfoList: MutableList<MenuInfo> = mutableListOf()

        menuArray.forEachIndexed { i, name ->
            val mInfo = MenuInfo(
                name, i
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
            0 -> { // 단어 입력하기(Editor)
                nav().navigate(R.id.plainTextFragment)
            }
            1 -> { // 단어 입력하기(Camera)
                nav().navigate(R.id.cameraEditFragment)
            }
            2 -> { // 파일 불러오기

            }
            3 -> { // 암기 일정 편집
                nav().navigate(R.id.wordScheduleFragment)

            }
            4 -> { // 단어 목록 편집
                nav().navigate(R.id.vocaEditFragment)
            }
            else -> {

            }
        }
    }
}