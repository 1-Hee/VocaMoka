package com.aiden.vokamoka.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentEditWordBinding
import com.aiden.vokamoka.ui.viewmodel.EditWordViewModel
import com.aiden.vokamoka.ui.viewmodel.UserStatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class EditWordFragment : BaseFragment<FragmentEditWordBinding>(), ViewClickListener {

    private val editWordViewModel: EditWordViewModel by viewModels()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_edit_word,
            BR.vm, editWordViewModel)
            .addBindingParam(BR.click, this)
    }

    override fun initViewModel() { }

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
}