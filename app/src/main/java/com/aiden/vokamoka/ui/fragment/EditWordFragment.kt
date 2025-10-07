package com.aiden.vokamoka.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentEditWordBinding
import com.aiden.vokamoka.ui.viewmodel.EditWordViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditWordFragment : BaseFragment<FragmentEditWordBinding>(),
    ViewClickListener, TabLayout.OnTabSelectedListener {

    private lateinit var navController: NavController
    private val editWordViewModel: EditWordViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_edit_word,
            BR.vm, editWordViewModel)
            .addBindingParam(BR.click, this)
            .addBindingParam(BR.onTabSelect, this)
    }

    override fun initViewModel() { }

    override fun onStart() {
        super.onStart()
        showBackButton(true)
    }

    override fun initView() {
        navController = requireActivity().findNavController(R.id.nav_host_edit_word)
    }

    override fun onViewClick(view: View) {
        when(view.id){

            else -> {

            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        navController.popBackStack()
        when(tab?.position){
            0 -> { // Plain Text (= 메모장)
                navController.navigate(R.id.plainTextFragment)
            }
            1 -> { // Camera Edit
                navController.navigate(R.id.cameraEditFragment)
            }
            else -> {

            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) { }
    override fun onTabReselected(tab: TabLayout.Tab?) { }

    /*
val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        Toast.makeText(this@MainActivity, "${tab.text} 선택됨", Toast.LENGTH_SHORT).show()
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}
    override fun onTabReselected(tab: TabLayout.Tab) {}
})

     */
}