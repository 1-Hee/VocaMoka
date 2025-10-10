package com.aiden.vokamoka.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.aiden.vokamoka.BR
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.listener.ViewClickListener
import com.aiden.vokamoka.base.ui.BaseFragment
import com.aiden.vokamoka.databinding.FragmentCameraEditBinding
import com.aiden.vokamoka.ui.viewmodel.CameraEditViewModel
import com.aiden.vokamoka.util.CameraTools
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import java.io.IOException

@AndroidEntryPoint
class CameraEditFragment : BaseFragment<FragmentCameraEditBinding>(), ViewClickListener {

    private val TAG = this.javaClass.simpleName
    private val cameraEditViewModel: CameraEditViewModel by viewModels()
    @Inject
    lateinit var cameraTools: CameraTools

    // 카메라 액티비티 팝업용 런쳐
    private lateinit var launcher: ActivityResultLauncher<Intent>

    // 사용자 권한 설정 요청용 런처
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_camera_edit,
            BR.vm, cameraEditViewModel)
            .addBindingParam(BR.click, this)
    }

    override fun initViewModel() { }

    // 사용자 권한 체크 함수
    private fun checkUserPermission() {
        val appPermission: MutableList<String> = mutableListOf()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            appPermission += arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            appPermission += arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        appPermission += Manifest.permission.CAMERA
        permissionLauncher.launch(appPermission.toTypedArray())
    }

    // 카메라 호출 메서드
    private fun showCamera(){
        // 카메라 권한 체크
        val cameraFlag = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (cameraFlag) {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Ensure that there's a camera activity to handle the intent
            if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
                // Create the File where the photo should go
                val photoFile = try {
                    cameraTools.createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.provider",
                        it
                    )
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcher.launch(cameraIntent)
                }
            }

        } else {
            checkUserPermission()
        }
    }

    override fun initView() {
        // 액티비티 종료시 결과릴 리턴받기 위한 콜백 함수
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val flag: Boolean = result.resultCode == Activity.RESULT_OK
            this.cameraEditViewModel.setIsImgReady(flag)
            if (flag) {
                cameraTools.createCaptureImages()
                cameraEditViewModel.setImgBitmap(cameraTools.acceptBitmap)
                mBinding.ivCapturedImage.setImageBitmap(cameraEditViewModel.imgBitmap.get())
                mBinding.notifyChange()
            }
        }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val deniedList = permissions.filterValues { !it }.keys
                if (deniedList.isEmpty()) {
                    // 모든 권한 허용됨
                } else {
                    // 거부된 권한이 있음

                }
            }

        // 권한 요청 실행
        checkUserPermission()
    }

    override fun onViewClick(view: View) {
        when(view.id){
            R.id.mcv_camera_pop_up -> {
                showCamera()
            }
            R.id.btn_load_image_save -> {
                cameraEditViewModel.setIsImgReady(false)
                cameraEditViewModel.setImgBitmap(null)

            }
            R.id.btn_load_image_close -> {
                cameraEditViewModel.setIsImgReady(false)
                cameraEditViewModel.setImgBitmap(null)

            }
            else -> {

            }
        }
    }
}