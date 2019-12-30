package com.flood_android.ui.postnourl

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.post.*
import com.flood_android.util.GlobalData
import com.flood_android.util.safeEnqueue
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post_no_url.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.InputStream

class PostNoUrlActivity : AppCompatActivity() {

    private var PICTURE_REQUEST_CODE: Int = 100
    val uploadImageList = ArrayList<PostPostImageData>()
    var images: ArrayList<MultipartBody.Part>? = ArrayList()
    var photobody: MultipartBody.Part? = null

    private val categoryDialog by lazy {
        PostCategoryDialog()
    }

    private val postSetCategoryDialog by lazy {
        PostSetCategoryDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_no_url)

        // 서버에서 조직 카테고리 GET
        getPostCategory()
        // 버튼 클릭 함수
        setBtnClickListner()
        // 하단 선택한 사진 리싸이클러뷰
        setImageRecyclerView()
    }

    /**
     * 서버에서 조직 카테고리 GET
     */
    private fun getPostCategory() {
        val getPostResponse = ApplicationController.networkServiceFeed
            .getPostResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58")
        getPostResponse.safeEnqueue {
            if (it.message == "그룹 카테고리 조회 성공") {
                GlobalData.categoryList = it.data.category
            }
        }
    }

    /**
     * 버튼 클릭 함수
     */
    private fun setBtnClickListner() {
        // 닫기 버튼
        iv_post_no_url_close.setOnClickListener {
            finish()
        }
        // 카테고리 변경 버튼
        iv_post_no_url_add_category.setOnClickListener {
            categoryDialog.show(supportFragmentManager, "category dialog")
            GlobalData.categoryDialogFalg = "1"
        }
        // 변경된 카테고리 버튼
        tv_post_no_url_selected_category.setOnClickListener {
            categoryDialog.show(supportFragmentManager, "category dialog")
            GlobalData.categoryDialogFalg = "1"
        }
        // 사진 앨범 버튼
        iv_post_no_url_image_add.setOnClickListener {
            try {
                // 접근 권한 허용 여부
                getPermission()
                setImageRecyclerView()
            } catch (e: Exception) {
                Log.e("사진앨범", "문제가 있습니다")
            }
        }
        // 게시물 올리기 버튼
        setPostBtn()

    }

    override fun onResume() {
        super.onResume()
        setImageRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
                val uri = data?.data
                if (uri == null) {
                    var clipData: ClipData?
                    clipData = data?.clipData
                    if (data.clipData!!.itemCount > 10)
                        Toast.makeText(this, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
                    if (clipData != null) {
                        for (i in 0 until data.clipData!!.itemCount) {
                            var item: ClipData.Item = data.clipData!!.getItemAt(i)

                            var selectedPictureUri: Uri = item.uri
                            val options = BitmapFactory.Options()
                            val inputStream: InputStream? =
                                contentResolver!!.openInputStream(selectedPictureUri)
                            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

                            val photobodyPre =
                                RequestBody.create(
                                    MediaType.parse("image/jpg"),
                                    byteArrayOutputStream.toByteArray()
                                )
                            // name에는 서버가 주는 키값 그대로 넣어줘야 한다.
                            photobody =
                                MultipartBody.Part.createFormData(
                                    "images",
                                    "fileName",
                                    photobodyPre
                                )

                            // 클립데이터의 uri을 리사이클러뷰 데이터 클래스에 추가하기.
                            uploadImageList.add(PostPostImageData(item.uri.toString()))
                            images?.add(photobody!!)
                            //여기의 image는 키값의 이름하고 같아야함
                        }
                    }
                } else {
                    var selectedPictureUri: Uri = uri
                    val options = BitmapFactory.Options()
                    val inputStream: InputStream? =
                        contentResolver!!.openInputStream(selectedPictureUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    val photoBodyPre =
                        RequestBody.create(
                            MediaType.parse("image/jpg"),
                            byteArrayOutputStream.toByteArray()
                        )
                    // 클립데이터의 uri을 리사이클러뷰 데이터 클래스에 추가하기.
                    uploadImageList.add(PostPostImageData(uri.toString()))
                    images?.add(
                        MultipartBody.Part.createFormData(
                            "images",
                            "fileName",
                            photoBodyPre
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("PostActivity", e.toString())
        }
    }

    /**
     * 접근 권한 허용 여부
     */
    private fun getPermission() {
        val permissionListener = object : PermissionListener {
            // 권한 요청 성공
            override fun onPermissionGranted() {
                // 갤러기 열기
                openGallery()
            }

            // 권한 요청 실패
            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                Toast.makeText(this@PostNoUrlActivity, "갤러리 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
            }
        }
        try {
            TedPermission.with(this@PostNoUrlActivity)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다")
                .setDeniedMessage("[설정] > [권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
                .check()
        } catch (e: java.lang.Exception) {
        }
    }

    /**
     * 갤러기 열기
     */
    fun openGallery() {
        var intent = Intent()
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setAction(Intent.ACTION_GET_CONTENT)
        //TODO : 기기 스펙에 따라 나오는 것이 다른 문제가 생김. 해서 기기 스펙과 intent 간의 상관관계 확인 필요

        startActivityForResult(
            Intent.createChooser(intent, "리뷰에 업로드할 사진을 선택해주세요.!"),
            PICTURE_REQUEST_CODE
        )
        // Toast.makeText(this@PostActivity, "사진을 꾹 눌러서 여러장을 선택해보세요~", Toast.LENGTH_LONG).show()
    }

    /**
     * 하단 선택한 사진 리싸이클러뷰
     */
    private fun setImageRecyclerView() {
        rv_post_no_url_image_list.apply {
            adapter = PostPostAdapter(this@PostNoUrlActivity, uploadImageList, images!!)
            layoutManager =
                LinearLayoutManager(this@PostNoUrlActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    /**
     * 게시 버튼 활성화
     */
    private fun setPostBtn() {
        var content = edt_post_no_url_content.text

        // 게시 버튼 파랑색으로
        edtPostToBlue(edt_post_no_url_content)

        // 게시물 등록 버튼
        tv_post_no_url_post.setOnClickListener {
            var category = tv_post_no_url_selected_category.text
            Log.e("PostNoUrlActivity", "게시 버튼 클릭됨")
            if (content.length == 0) {
                Log.e("PostNoUrlActivity", "content가 없음")
            } else {
                if (category.length == 0) {
                    Log.e("PostNoUrlActivity", "category가 없음")
                    postSetCategoryDialog.show(supportFragmentManager, "postSetCategoryDialog")
                } else {
                    Log.v("PostNoUrlActivity", "통신성공")
                    val post_url =
                        RequestBody.create(MediaType.parse("text/plain"), "")
                    val post_content =
                        RequestBody.create(MediaType.parse("text/plain"), content.toString())
                    val post_category =
                        RequestBody.create(MediaType.parse("text/plain"), category.toString())
                    postPost(
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58",
                        images, post_url, post_content, post_category
                    )
                }
            }
        }

    }

    /**
     * 게시 버튼 파란색으로 활성화
     */
    private fun edtPostToBlue(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s!!.length != 0) {
                    tv_post_no_url_post.setTextColor(Color.parseColor("#0057ff"))
                } else {
                    tv_post_no_url_post.setTextColor(Color.parseColor("#d1d1d1"))
                }
            }
        })
    }

    /**
     * 게시물 올리기 POST 통신
     */
    var fail: (Throwable) -> Unit = {
        Log.v("PostNoUrlActivity", it.toString())
    }
    var temp: (PostPostResponse) -> Unit = {
        Log.v("PostNoUrlActivity", it.message)
    }

    private fun postPost(
        token: String,
        images: ArrayList<MultipartBody.Part>?,
        url: RequestBody,
        category: RequestBody,
        content: RequestBody
    ) {
        val postPostResponse = ApplicationController.networkServiceFeed
            .postPostResponse(token, images, url, category, content)
        postPostResponse.safeEnqueue(fail, temp)
    }

    /**
     * 카테고리 선택 시 변경된 카테고리 적용
     */
    fun setNoUrlCategory(category: CharSequence?) {
        tv_post_no_url_selected_category.text = category.toString()
        tv_post_no_url_selected_category.visibility = View.VISIBLE
        iv_post_no_url_add_category.visibility = View.INVISIBLE
    }
}
