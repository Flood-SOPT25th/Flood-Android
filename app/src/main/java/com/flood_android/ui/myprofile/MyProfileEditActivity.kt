package com.flood_android.ui.myprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.myprofile.get.User
import com.flood_android.ui.myprofile.post.PostMyProfileEditResponse
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import com.flood_android.util.toast
import kotlinx.android.synthetic.main.activity_my_profile_edit.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class MyProfileEditActivity : AppCompatActivity() {

    private val MY_READ_STORAGE_REQUEST_CODE by lazy {
        1004
    }
    private val REQ_CODE_SELECT_IMAGE by lazy {
        100
    }

    lateinit var imageURI: String

    private var mImage: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_edit)

        // 서버에서 계정 정보 가져오기
        getMyProfile()
        // 버튼
        setBtn()
    }

    private fun getMyProfile() {
        val getMyProfileEditResponse = ApplicationController.networkServiceUser
            .getMyProfileEditResponse(SharedPreferenceController.getAuthorization(this@MyProfileEditActivity)!!)
        getMyProfileEditResponse.safeEnqueue {
            setView(it.data.user, it.data.group.name)
        }
    }

    private fun setView(userData: User, groupName: String) {
        Glide.with(this)
            .load(userData.profileImage)
            .transform(CenterCrop(), CircleCrop())
            .into(iv_myprofile_edit_image)

        tv_myprofile_edit_name.setText(userData.name)
        tv_myprofile_edit_position.setText(userData.rank)
        tv_myprofile_edit_department.text = groupName
        tv_myprofile_edit_email.text = userData.email
        tv_myprofile_edit_tel.setText(userData.phone)

        if (userData.admin) {
            txt_org_set.visibility = View.VISIBLE
            iv_myprofile_edit_org_set.visibility = View.VISIBLE
        }
    }

    private fun setBtn() {
        iv_myprofile_edit_back.setOnClickListener {
            finish()
        }
        tv_myprofile_edit_done.setOnClickListener {
            // 바뀐 계정 서버 통신
            beforeServer()
        }
        iv_myprofile_edit_password_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, PasswordSetActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_alarm_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, AlarmSetActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_invite_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, InviteActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_center.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, CenterActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_logout.setOnClickListener {
            LogoutDialog().show(supportFragmentManager, "Logout Dialog")
        }
        // 사진 앨범에서 가져오기
        tv_myprofile_edit.setOnClickListener {
            requestReadExternalStoragePermission()
        }
    }

    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }

    private fun showAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val selectedImageUri: Uri = it.data!!
                    val options = BitmapFactory.Options()
                    val inputStream: InputStream =
                        contentResolver.openInputStream(selectedImageUri)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    imageURI = getRealPathFromURI(selectedImageUri)
                    val photoBody =
                        RequestBody.create(
                            MediaType.parse("image/jpg"),
                            byteArrayOutputStream.toByteArray()
                        )
                    mImage = MultipartBody.Part.createFormData(
                        "image",
                        File(selectedImageUri.toString()).name,
                        photoBody
                    )

                    Glide.with(this)
                        .load(selectedImageUri)
                        .transform(CenterCrop(), CircleCrop())
                        .thumbnail(0.1f)
                        .into(iv_myprofile_edit_image)
                }
            }
        }
    }

    private fun getRealPathFromURI(content: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, content, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }

    private fun beforeServer() {
        var userName = tv_myprofile_edit_name.text
        var userPosition = tv_myprofile_edit_position.text
        var userTel = tv_myprofile_edit_tel.text

        if (userName.length != 0 && userPosition.length != 0 && userTel.length != 0) {
            Log.e("청하", userName.toString())
            Log.e("청하", userPosition.toString())
            Log.e("청하", userTel.toString())

            val userName =
                RequestBody.create(MediaType.parse("text/plain"), userName.toString())
            val userPosition =
                RequestBody.create(MediaType.parse("text/plain"), userPosition.toString())
            val userTel =
                RequestBody.create(MediaType.parse("text/plain"), userTel.toString())

            postMyProfileEdit(
                SharedPreferenceController.getAuthorization(this@MyProfileEditActivity)!!,
                mImage, userName, userPosition, userTel)
            Log.e("청하", userName.toString())
            Log.e("청하", userPosition.toString())
            Log.e("청하", userTel.toString())
        } else {
            MyProfileAlerDialog().show(supportFragmentManager, "myprofile aler dialog")
        }

    }
    /**
     * 프로필 설정 POST 통신
     */
    var fail: (Throwable) -> Unit = {
        Log.v("MyProfileEditActivity", it.toString())
    }
    var temp: (PostMyProfileEditResponse) -> Unit = {
        Log.v("MyProfileEditActivity", it.message)
        toast(it.message)
        finish()
    }
    private fun postMyProfileEdit(
        token: String,
        images: MultipartBody.Part?,
        userName: RequestBody,
        userPosition: RequestBody,
        userTel: RequestBody
    ) {
        val postMyProfileEditResponse = ApplicationController.networkServiceUser
            .postMyProfileEditResponse(token, images, userName, userPosition, userTel)
        postMyProfileEditResponse.safeEnqueue(fail, temp)
    }


    fun closeActivity() {
        finish()
    }

}
