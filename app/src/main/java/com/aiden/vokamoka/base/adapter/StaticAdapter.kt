package com.aiden.vokamoka.base.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aiden.vokamoka.base.listener.OnPageInfoListener
import com.aiden.vokamoka.data.dto.Permission
import com.aiden.vokamoka.data.dto.SettingItem
import com.aiden.vokamoka.databinding.ItemPermissionBinding
import com.aiden.vokamoka.databinding.ItemSettingBinding
import com.aiden.vokamoka.ui.adapter.VocaAdapter

class StaticAdapter {

    companion object {
        private val TAG = this.javaClass.simpleName

        // Voca Fragment Static Adapter
        @JvmStatic
        @BindingAdapter(value = ["vocaFragments", "onPageListener" ], requireAll = false)
        fun setVocaFragments(
            viewPager2: ViewPager2,
            fragments:List<Fragment>,
            onPageListener: OnPageInfoListener?
        ) {
            Log.i(TAG, "Call setVocaFragments()... size ${fragments.size}")
            val mActivity: FragmentActivity = viewPager2.context as? FragmentActivity?:return
            val adapter = VocaAdapter(mActivity)
            adapter.setFragment(fragments)
            viewPager2.adapter = adapter

            // 콜백 리스너 등록
            viewPager2.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onPageListener?.onPageChanged(position, adapter.itemCount)
                }
            })
        }

        // User Permissions ....
        @JvmStatic
        @BindingAdapter(value = ["permissionInfoList", "vClick"], requireAll = true)
        fun setPermissionAdapter(recyclerView: RecyclerView, infoList:List<Permission>, viewClick: ViewClickListener){
            val llm = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false);
            recyclerView.layoutManager = llm
            val adapter = object : BaseDataBindingAdapter<
                    Permission, ItemPermissionBinding>(recyclerView.context) {
                override fun getDataBindingConfig(): DataBindingConfig {
                    return DataBindingConfig(R.layout.item_permission)
                        .addBindingParam(BR.click, viewClick)
                }

                override fun onBindItem(
                    binding: ItemPermissionBinding,
                    position: Int,
                    item: Permission,
                    holder: RecyclerView.ViewHolder
                ) {
                    binding.permissionInfo = item
                    binding.notifyChange()
                }
            }

            adapter.setItemList(infoList)
            recyclerView.adapter = adapter
        }

        // Setting Items...
        @JvmStatic
        @BindingAdapter(value = ["settingItemList", "vClick"], requireAll = true)
        fun setSettingAdapter(recyclerView: RecyclerView, settingItemList:List<SettingItem>, viewClick:ViewClickListener){
            val llm = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false);
            recyclerView.layoutManager = llm

            val adapter = object : BaseDataBindingAdapter<
                    SettingItem, ItemSettingBinding>(recyclerView.context) {
                override fun getDataBindingConfig(): DataBindingConfig {
                    return DataBindingConfig(R.layout.item_setting)
                        .addBindingParam(BR.click, viewClick)
                }

                override fun onBindItem(
                    binding: ItemSettingBinding,
                    position: Int,
                    item: SettingItem,
                    holder: RecyclerView.ViewHolder
                ) {
                    binding.settingInfo = item
                    binding.notifyChange()
                }


            }
            adapter.setItemList(settingItemList)
            recyclerView.adapter = adapter
        }

    }
}