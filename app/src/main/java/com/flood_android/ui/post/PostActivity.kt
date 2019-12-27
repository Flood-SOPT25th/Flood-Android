package com.flood_android.ui.post

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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class PostActivity : AppCompatActivity() {

    /* val networkService: NetworkService by lazy {
         ApplicationController.instance.networkService
     }*/

    private var PICTURE_REQUEST_CODE: Int = 100
    val uploadImageList = ArrayList<PostPostImageData>()
    var images = ArrayList<MultipartBody.Part>()

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
                    /*if (data.clipData.itemCount > 9)
                        Toast.makeText(this, "사진은 9장까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
*/
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
                            val photoBody =
                                RequestBody.create(
                                    MediaType.parse("image/jpg"),
                                    byteArrayOutputStream.toByteArray()
                                )


                            // 클립데이터의 uri을 리사이클러뷰 데이터 클래스에 추가하기.
                            uploadImageList.add(PostPostImageData(item.uri.toString()))
                            images.add(
                                MultipartBody.Part.createFormData(
                                    "img",
                                    File(selectedPictureUri.toString()).name, photoBody
                                )
                            )//여기의 image는 키값의 이름하고 같아야함
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

                    // 클립데이터의 uri을 리사이클러뷰 데이터 클래스에 추가하기.
                    uploadImageList.add(PostPostImageData(uri.toString()))
                    images.add(
                        MultipartBody.Part.createFormData(
                            "img",
                            File(selectedPictureUri.toString()).name,
                            photoBody
                        )
                    )//여기의 image는 키값의 이름하고 같아야함
                }
            }
        } catch (e: Exception) {
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
            // 카테고리 변경 다이얼로그 띄우기
            var orgCategory = arrayListOf("IT", "디자인", "컴퓨터")

            val bundle = Bundle()
            bundle.putSerializable("categoryList", orgCategory)
            categoryDialog.arguments = bundle

            categoryDialog.show(supportFragmentManager, "category dialog")
            // 띄울 때 GET으로 받은 카테고리 보내기

            // 다이얼로그 변경된 값 보내기
            // 다이얼로그에서 선택된 값 받아오기
        }
        // 변경된 카테고리 버튼
        tv_post_selected_category.setOnClickListener {
            // 카테고리 변경 다이얼로그 띄우기
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
    /*private fun postPost(token: String, images: MultipartBody.Part, url: RequestBody, category: RequestBody, content: RequestBody) {
        val postPostResponse = networkService.postPostResponse(token, images, url, category, content)
        postPostResponse.enqueue(object : Callback<PostPostResponse> {
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
        })
    }
*/
    private fun getPermission() {
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
        var intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(intent, "얌얌굿즈 : 리뷰에 업로드할 사진을 선택해주세요.!"),
            PICTURE_REQUEST_CODE
        )
        Toast.makeText(this@PostActivity, "사진을 꾹 눌러서 여러장을 선택해보세요~", Toast.LENGTH_LONG).show()
    }

    // 하단 사진 리싸이클러뷰
    private fun configureRecyclerView() {
        rv_post_image_list.apply {
            adapter = PostPostAdapter(this@PostActivity, uploadImageList, images)
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
        var category = tv_post_selected_category.text

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
            Log.e("청하", "게시버튼")
            if (url.length == 0 && content.length == 0) {
                Log.e("청하", "url과 content 없음")
            } else {
                Log.e("청하", "게시버튼 활성화")
                Log.e("청하", tv_post_selected_category.text.toString())
                if (url.length > 0) {
                    if (category.length == 0) {
                        Log.e("청하", tv_post_selected_category.text.toString())
                        Log.e("청하", category.length.toString())
                        postSetCategoryDialog.show(supportFragmentManager, "postSetCategoryDialog")
                    } else {
                        Log.e("청하", "서버통신")
                        // 여기서 서버 통신
                        // postPost()
                    }
                }
            }

        }
    }
}
