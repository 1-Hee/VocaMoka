package com.aiden.vokamoka.base.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ItemClickListener
import com.aiden.vokamoka.base.listener.OnPageInfoListener
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.data.dto.Permission
import com.aiden.vokamoka.data.dto.SettingItem
import com.aiden.vokamoka.data.dto.StatInfo
import com.aiden.vokamoka.data.vo.DisplayWord
import com.aiden.vokamoka.data.vo.MenuInfo
import com.aiden.vokamoka.databinding.ItemMenuVocaLoadBinding
import com.aiden.vokamoka.databinding.ItemPermissionBinding
import com.aiden.vokamoka.databinding.ItemSettingBinding
import com.aiden.vokamoka.databinding.ItemStatMenuBinding
import com.aiden.vokamoka.ui.adapter.VocaAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class StaticAdapter {

    companion object {
        private val TAG = this.javaClass.simpleName

        // Statistics Menu Static Adapter
        @JvmStatic
        @BindingAdapter(value = ["statMenuList", "statInfoClick"], requireAll = false)
        fun setStatMenuList(
            recyclerView: RecyclerView,
            statMenuList:List<StatInfo>,
            statInfoClick: ItemClickListener<StatInfo>?
        ) {
            val flexboxLayoutManager = FlexboxLayoutManager(recyclerView.context).apply {
                flexDirection = FlexDirection.ROW          // 가로 방향으로 나열
                flexWrap = FlexWrap.WRAP                   // 줄바꿈 허용 → 2열 이상 가능
                justifyContent = JustifyContent.SPACE_EVENLY // 왼쪽 정렬
                alignItems = AlignItems.CENTER
            }
            recyclerView.layoutManager = flexboxLayoutManager

            val adapter = object
                : BaseDataBindingAdapter<StatInfo, ItemStatMenuBinding>(recyclerView.context) {
                override fun getDataBindingConfig(): DataBindingConfig {
                    return DataBindingConfig(R.layout.item_stat_menu)
                }

                override fun onBindItem(
                    binding: ItemStatMenuBinding,
                    position: Int,
                    item: StatInfo,
                    holder: RecyclerView.ViewHolder
                ) {
                    binding.setVariable(BR.statInfo, item)
                    binding.mcvStatInfo.setOnClickListener { v ->
                        statInfoClick?.onItemClick(v, position, item)
                    }
                }
            }

            adapter.setItemList(statMenuList)
            recyclerView.adapter = adapter

        }

        // Voca Load Menu Static Adapter
        @JvmStatic
        @BindingAdapter(value = ["vocaMenuList", "vocaMenuClick"], requireAll = false)
        fun setVocaMenuList(
            recyclerView: RecyclerView,
            vocaMenuList:List<MenuInfo>,
            vocaMenuClick: ItemClickListener<MenuInfo>
        ) {
            val llm = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false);
            recyclerView.layoutManager = llm
            val adapter = object
                : BaseDataBindingAdapter<MenuInfo, ItemMenuVocaLoadBinding>(recyclerView.context) {
                override fun getDataBindingConfig(): DataBindingConfig {
                    return DataBindingConfig(R.layout.item_menu_voca_load)
                }

                override fun onBindItem(
                    binding: ItemMenuVocaLoadBinding,
                    position: Int,
                    item: MenuInfo,
                    holder: RecyclerView.ViewHolder
                ) {
                    binding.setVariable(BR.menuInfo, item)
                    binding.mcvMenuInfo.setOnClickListener { v ->
                        vocaMenuClick.onItemClick(v, position, item)
                    }
                }
            }

            adapter.setItemList(vocaMenuList)
            recyclerView.adapter = adapter
        }



        // Voca Fragment Static Adapter
        @JvmStatic
        @BindingAdapter(value = ["vocaInfoList", "onPageListener", "vocaAdapter" ], requireAll = false)
        fun setVocaFragments(
            viewPager2: ViewPager2,
            vocaInfoList:List<DisplayWord>,
            onPageListener: OnPageInfoListener?,
            vocaAdapter: VocaAdapter?
        ) {
            Log.i(TAG, "Call setVocaFragments()... size ${vocaInfoList.size}")
            if(vocaInfoList.isEmpty()) return
            vocaAdapter?:return

            vocaAdapter.setDisplayWord(vocaInfoList)
            viewPager2.adapter = vocaAdapter

            // 콜백 리스너 등록
            var isFirstCallback = true
            // 첫 번째 실제 아이템으로 이동
            viewPager2.setCurrentItem(1, false)
            onPageListener?.onPageChanged(0, vocaInfoList.size)
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        val pos = viewPager2.currentItem
                        val last = vocaAdapter.itemCount - 1
                        when (pos) {
                            0 -> viewPager2.setCurrentItem(last - 1, false)
                            last -> viewPager2.setCurrentItem(1, false)
                        }
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (isFirstCallback) {
                        isFirstCallback = false
                        return
                    }

                    val realPosition = when (position) {
                        0 -> vocaAdapter.itemCount - 3     // 0번째(왼쪽 끝) → 마지막 실제 인덱스
                        vocaAdapter.itemCount - 1 -> 0     // 마지막(오른쪽 끝) → 첫 번째 실제 인덱스
                        else -> position - 1           // 나머지는 1 빼기
                    }
                    onPageListener?.onPageChanged(realPosition, vocaAdapter.itemCount - 2)
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