package com.flood_android.ui.post

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.myNameIS09
import com.flood_android.util.safeEnqueue
import com.flood_android.util.toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class PostActivity : AppCompatActivity() {

    /* val networkService: NetworkService by lazy {
         ApplicationController.instance.networkService
     }*/

    private var PICTURE_REQUEST_CODE: Int = 100
    val uploadImageList = ArrayList<PostPostImageData>()
    var images : ArrayList<MultipartBody.Part>? = ArrayList()
    var photobody: MultipartBody.Part? = null

    private val categoryDialog by lazy {
        PostCategoryDialog()
    }

    private val postSetCategoryDialog by lazy {
        PostSetCategoryDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        // 초기 서버 통신 GET - 카테고리, 명단
        // 버튼 클릭 함수
        setBtnClickListner()
        // 사진 리싸이클러뷰
        configureRecyclerView()
        setPostBtn()
    }


    override fun onResume() {
        super.onResume()
        configureRecyclerView()
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

                            Log.v("Postygyg", "들어오더랗")
                            val photobodyPre =
                                RequestBody.create(
                                    MediaType.parse("image/jpg"),
                                    byteArrayOutputStream.toByteArray()
                                )
                            photobody = MultipartBody.Part.createFormData("images", "abcd", photobodyPre)

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
                    val photoBody =
                        RequestBody.create(
                            MediaType.parse("image/jpg"),
                            byteArrayOutputStream.toByteArray()
                        )
                    Log.v("Postygyg", "들어오더랗X")

                    // 클립데이터의 uri을 리사이클러뷰 데이터 클래스에 추가하기.
                    uploadImageList.add(PostPostImageData(uri.toString()))
                    images?.add(
                        MultipartBody.Part.createFormData(
                            "images",
                            "Abcd",
                            photoBody
                        )
                    )//여기의 image는 키값의 이름하고 같아야함
                }
            }
        } catch (e: Exception) {
            Log.v("Postygyg", e.toString())
        }
    }

    // 버튼 클릭 함수
    private fun setBtnClickListner() {
        // 닫기 버튼
        iv_post_close.setOnClickListener {
            finish()
        }
        // 카테고리 변경 버튼
        iv_post_add_category.setOnClickListener {
            var orgCategory = arrayListOf("IT", "디자인", "컴퓨터")

            val bundle = Bundle()
            bundle.putSerializable("categoryList", orgCategory)
            categoryDialog.arguments = bundle

            categoryDialog.show(supportFragmentManager, "category dialog")
        }
        // 변경된 카테고리 버튼
        tv_post_selected_category.setOnClickListener {
            var orgCategory = arrayListOf("IT", "컴퓨터")
            val bundle = Bundle()
            bundle.putSerializable("categoryList", orgCategory)
            categoryDialog.arguments = bundle
            categoryDialog.show(supportFragmentManager, "category dialog")
        }
        // 사진 앨범 버튼
        iv_post_image_add.setOnClickListener {
            try {
                getPermission()
                //openGallery()
                configureRecyclerView()
            } catch (e: Exception) {
                Log.e("사진앨범", "문제가 있습니다")
            }
        }
    }

    // 게시물 올리기 통신
    var temp: (PostPostResponse) -> Unit = {
       Log.v("Postygyg", it.message)
    }

    var fail: (Throwable) -> Unit = {
        Log.v("Postygyg", it.toString())
    }

    private fun postPost(
        token: String,
        images: ArrayList<MultipartBody.Part>?,
        url: RequestBody,
        category: RequestBody,
        content: RequestBody
    ) {
        val postPostResponse = ApplicationController.networkServiceFeed
            .postPostResponse(token,images, url, category, content)
        val message: String = "12121212"

        postPostResponse.safeEnqueue (fail, temp)


        Log.v("aaaa", message)


        // 게시물 올리기 통신
        /*postPostResponse.enqueue(object : Callback<PostPostResponse> {
            override fun onFailure(call: Call<PostPostResponse>, t: Throwable) {
                Log.e("PostPost fail", t.toString())
            }

            override fun onResponse(
                call: Call<PostPostResponse>,
                response: Response<PostPostResponse>
            ) {
                // 작품 등록 성공
                response?.takeIf { it.isSuccessful }
                    ?.body()?.takeIf { it.message == "성공" }
                    ?.let {
                        Log.e("통신", "성공")
                    }
            }
        })*/
//
//        val postPostResponse = PostPostResponse("aaa")
//        val post =
    }

    private fun getPermission() {
//        val textRequest = RequestBody.create(MediaType.parse("text/plain"), "dssdsds")
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // 권한 요청 성공
                openGallery()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                // 권한 요청 실패
                Toast.makeText(this@PostActivity, "갤러리 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show()
            }
        }
        try {
            Log.v("청하", "들어옴")
            TedPermission.with(this@PostActivity)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다")
                .setDeniedMessage("[설정] > [권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
                .check()
        } catch (e: java.lang.Exception) {
            Log.v("청하", "권한 설정 실패")
        }
    }

    // 갤러리 열기
    fun openGallery() {

//        var intent = Intent(Intent.ACTION_PICK)
//        intent.type = MediaStore.Images.Media.CONTENT_TYPE
//        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)

        //var intent = Intent(Intent.ACTION_PICK)
        var intent = Intent()
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setAction(Intent.ACTION_GET_CONTENT)
        //TODO : 기기 스펙에 따라 나오는 것이 다른 문제가 생김. 해서 기기 스펙과 intent 간의 상관관계 확인 필요


        startActivityForResult(
            Intent.createChooser(intent, "얌얌굿즈 : 리뷰에 업로드할 사진을 선택해주세요.!"),
            PICTURE_REQUEST_CODE
        )
        Toast.makeText(this@PostActivity, "사진을 꾹 눌러서 여러장을 선택해보세요~", Toast.LENGTH_LONG).show()
    }

    // 하단 사진 리싸이클러뷰
    private fun configureRecyclerView() {
        rv_post_image_list.apply {
            adapter = PostPostAdapter(this@PostActivity, uploadImageList, images!!)
            layoutManager =
                LinearLayoutManager(this@PostActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    // 카테고리 변경 설정
    fun setCategory(category: CharSequence?) {
        tv_post_selected_category.text = category.toString()
        tv_post_selected_category.visibility = View.VISIBLE
        iv_post_add_category.visibility = View.INVISIBLE
        Log.e("청하", tv_post_selected_category.text.toString())
    }

    private fun setPostBtn() {
        var url = edt_post_url.text
        var content = edt_post_content.text

        edt_post_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s!!.length != 0) {
                    tv_post_post.setTextColor(Color.parseColor("#0057ff"))
                } else {
                    tv_post_post.setTextColor(Color.parseColor("#d1d1d1"))
                }
            }
        })
        edt_post_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s!!.length != 0) {
                    tv_post_post.setTextColor(Color.parseColor("#0057ff"))
                } else {
                    tv_post_post.setTextColor(Color.parseColor("#d1d1d1"))
                }
            }
        })

        tv_post_post.setOnClickListener {
            Log.e("청하", "1")
            var category = tv_post_selected_category.text.toString()
            if (url.length == 0 && content.length == 0) {
            } else {
                Log.e("청하", "3")
                if (url.length > 0 && category.length == 0) {
                    Log.e("청하", "4")
                    postSetCategoryDialog.show(supportFragmentManager, "postSetCategoryDialog")
                } else {
                    // 여기서 서버 통신
                    val url2 = RequestBody.create(MediaType.parse("text/plain"), "https://www.naver.com/")
                    val content2 = RequestBody.create(MediaType.parse("text/plain"), "content")
                    val category2 = RequestBody.create(MediaType.parse("text/plain"), "category")
                    //photobody = MultipartBody.Part.createFormData("images", "", "32323")
                    postPost("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58",
                        images, url2, category2, content2)
                }
            }
        }
    }
}
