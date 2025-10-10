package com.aiden.vokamoka.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

@Singleton
class CameraTools @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val TAG = this.javaClass.simpleName

    /**
     *  Variables
     */
    private var _fileName: String = "" // 저장된 파일 이름
    private var _acceptBitmap: Bitmap? = null // // 카메라로부터 받은 비트맵
    private var _imageUri: Uri? = null // 변환된 이미지 Uri
    private var _photoFile: File? = null // 촬영된 카메라 이미지를 저장할 파일 객체
    private var _currentPhotoPath: String = "" // 저장된 사진 파일 경로

    /**
     *  Getter
     */
    val fileName: String get() = _fileName
    val acceptBitmap: Bitmap? get() = _acceptBitmap
    val imgUri: Uri? get() = _imageUri
    val photoFile: File? get() = _photoFile
    val currentPhotoPath: String get() = _currentPhotoPath

    /**
     *  Setter
     */
    fun setFileName(fileName : String){
        this._fileName = fileName
    }

    fun setAcceptBitmap(acceptBitmap: Bitmap?) {
        this._acceptBitmap = acceptBitmap

    }

    fun setImgUri(imgUri : Uri?) {
        this._imageUri = imgUri
    }

    fun setPhotoFile(photoFile : File?) {
        this._photoFile = photoFile
    }

    fun setCurrentPhotoPath (currentPhotoPath : String) {
        this._currentPhotoPath = currentPhotoPath
    }

    // 비트맵 이미지 회전 메서드
    private fun rotateBitmapImage(bitmap: Bitmap): Bitmap {
        val photoPath = currentPhotoPath
        val rotationDegrees = getRotationDegrees(photoPath)
        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    // 회전 각도 계산 메서드
    private fun getRotationDegrees(photoPath: String): Int {
        return try {
            val exifInterface = ExifInterface(photoPath)
            when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
            0
        }
    }

    // 카메라로부터 받아온 이미지 비트맵을 uri로 저장하는 메서드
    fun createCaptureImages(){
        try {
            val bitmap: Bitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            val logMsg: String = "photo .. width : %d , height : %d , density : %d"
                .format(bitmap.width, bitmap.height, bitmap.density)
            Log.i(TAG, logMsg)
            val rotatedBitmap = rotateBitmapImage(bitmap)
            setAcceptBitmap(rotatedBitmap)
            saveCapturedPhoto(rotatedBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            // Toast.makeText(this, "이미지를 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 카메라로부터 받아온 이미지 데이터를 파일 데이터로 생성하는 메서드
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // PICTURES 경로에 완성이 되어야 함.
        val tempFile:File = File.createTempFile(
            "${context.packageName}_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */ // 저장될 경로
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            setCurrentPhotoPath(absolutePath)
        }
        setFileName(tempFile.name)
        setPhotoFile(tempFile)
        return tempFile
    }

    // 저장된 카메라 비트맵을 저장하는 메서드
    private fun saveCapturedPhoto(bitmap:Bitmap) {
        /**
         * 파일 이름 저장
         */
        val resolver: ContentResolver = context.contentResolver
        // 비트맵을 저장할 경로 생성
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        // 저장된 이미지를 컨텐츠 리졸버에 저장함.
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        setImgUri(imageUri)
        imageUri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
            }
        }
        // 파일 생성 후 미디어 스캐닝 기능
        imageUri?.path.let { newPath ->
            val file = File(newPath)
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()),
                null, null)
        }
    }

}