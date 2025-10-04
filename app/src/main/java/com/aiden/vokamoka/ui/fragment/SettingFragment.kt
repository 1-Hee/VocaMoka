package com.aiden.vokamoka.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.BuildConfig
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.data.dto.Permission
import com.aiden.vokamoka.data.dto.SettingItem
import com.aiden.vokamoka.databinding.FragmentSettingBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import java.util.Locale
import androidx.core.net.toUri

class SettingFragment : BaseFragment<FragmentSettingBinding>(){

    // 다른 액티비티 이동후 결과 값을 받아 핸들링할 런쳐
    private lateinit var launcher: ActivityResultLauncher<Intent>;

    private lateinit var permissionInfoList:MutableList<Permission>
    private lateinit var settingOptionList:MutableList<SettingItem>

    override fun initViewModel() {
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        permissionInfoList = mutableListOf()
        settingOptionList = mutableListOf()

        // set User permissions...
        val context = requireContext()
        val nameArray:Array<String> = context.resources
            .getStringArray(R.array.arr_user_permission); // 사용자 권한 명
        val guideArray:Array<String> = context.resources
            .getStringArray(R.array.arr_user_permission_desc); // 사용자 권한 설명
        val iconArray: TypedArray = context.resources.obtainTypedArray(R.array.icon_user_permissions) // 아이콘

        for(i in nameArray.indices){
            val drawable: Drawable? = iconArray.getDrawable(i)
            val mPermission = Permission(nameArray[i], guideArray[i], drawable, false)
            permissionInfoList.add(mPermission)
        }
        iconArray.recycle()

        // Setting Option...
        val settingArray:Array<String> = context.resources
            .getStringArray(R.array.arr_setting_option); // 메뉴 명

        for(i in 0..2) {
            val settingItem = SettingItem(settingArray[i], "")
            when(i){
                0 -> { // 언어 설정
                    val langStr:String = Locale.getDefault().toLanguageTag()
                    settingItem.value = langStr
                }
                1 -> { // 앱 업데이트
                    settingItem.value = BuildConfig.VERSION_NAME
                }
            }
            settingOptionList.add(settingItem)
        }

        return DataBindingConfig(R.layout.fragment_setting)
            .addBindingParam(BR.settingItemList, settingOptionList)
            .addBindingParam(BR.permissionInfoList, permissionInfoList)
            .addBindingParam(BR.pClick, permissionClickListener)
            .addBindingParam(BR.sClick, settingClickListener)
    }

    override fun initView() {
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { // 액티비티 종료시 결과릴 리턴받기 위한 콜백 함수
            val cancelFlag = it.resultCode == Activity.RESULT_CANCELED;
            if (it.resultCode == Activity.RESULT_OK) {
                // 데이터 찍어볼경우!
                /*
                val intent = result.data
                val resultState = intent?.getStringExtra("newAlbumName")
                Logger.i("resultState : %s", resultState)
                 */
            }
        }
        // hideSettingMenu() // 세팅 메뉴 숨김
        checkUserPermission() // 사용자 권한 체크
    }

    // 권한 변경 페이지로 이동
    private fun popUpToAppSetting(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        Toast.makeText(requireContext(), getString(R.string.txt_guide_modify_permission), Toast.LENGTH_SHORT).show()
        launcher.launch(intent)
    }

    // 사용자 권한 체크 함수
    private fun checkUserPermission() {
        // 저장소 권한 체크
        val isStorageAllowed = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            val storagePermission = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            var flag = false;
            storagePermission.forEach { it ->
                flag = ContextCompat.checkSelfPermission(
                    requireContext(), it
                ) == PackageManager.PERMISSION_GRANTED || flag
            }
            flag
        }else true

        val mPermission = this.permissionInfoList[0]
        mPermission.isAllowed = isStorageAllowed
        this.permissionInfoList[0] = mPermission
        mBinding.setVariable(BR.permissionInfoList, permissionInfoList)
        mBinding.notifyChange()
    }

    // Listener
    // 사용자 권한 클릭 리스너
    private val permissionClickListener = object : ViewClickListener {
        override fun onViewClick(view: View) {
            val context = view.context
            val nameArray:Array<String> = context.resources
                .getStringArray(R.array.arr_user_permission); // 사용자 권한 명

            when(view.tag){
                nameArray[0] -> { // 저장소 권한
                    popUpToAppSetting()
                }
                else -> {

                }
            }

        }
    }

    // 세팅 클릭 리스너
    private val settingClickListener = object : ViewClickListener {
        override fun onViewClick(view: View) {
            val context = view.context
            val settingArray:Array<String> = context.resources
                .getStringArray(R.array.arr_setting_option); // 세팅 옵션 명

            when(view.tag){
                settingArray[0] -> { // 언어 설정
                    val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                    launcher.launch(intent)
                }
                settingArray[1] -> { // 앱 업데이트 이동
                    try {
                        val mPlayUrl = context.getString(R.string.app_play_store_url)
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setPackage("com.android.vending")
                            data = mPlayUrl.toUri()
                        }
                        startActivity(intent)
                    } catch (_:Exception){
                        Toast.makeText(
                            context,
                            getString(R.string.msg_invalid_vending),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                settingArray[2] -> { // 오픈 소스 라이선스 ...
                    val intent = Intent(requireActivity(), OssLicensesMenuActivity::class.java)
                    launcher.launch(intent)
                }
            }
        }
    }
}