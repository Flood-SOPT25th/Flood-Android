package com.flood_android.ui.firstlogin


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode2.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class GroupCreationFragment4 : Fragment() {
    private var nameFlag = false
    private var rankFlag = false

    private var profileImage: MultipartBody.Part? = null

    private val MY_READ_STORAGE_REQUEST_CODE by lazy {
        1004
    }
    private val REQ_CODE_SELECT_IMAGE by lazy {
        100
    }

    lateinit var imageURI : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_login_withgroupcode2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtxt_first_login_withgroupcode2_profile_name.addTextChangedListener(nameWatcher)
        edtxt_first_login_withgroupcode2_profile_rank.addTextChangedListener(rankWatcher)
        iv_first_login_withgroupcode2_editpart.setOnClickListener {
            requestReadExternalStoragePermission()
        }
    }

    private val nameWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                nameFlag = true
                if (rankFlag) {
                    toSignal(true)
                    toSignin2()
                }
                else
                    toSignal(false)
            } else {
                toSignal(false)
                nameFlag = false
            }
        }
    }

    private val rankWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                rankFlag = true
                if (nameFlag) {
                    toSignal(true)
                    toSignin2()
                }
                else
                    toSignal(false)
            } else {
                toSignal(false)
                rankFlag = false
            }
        }
    }

    fun toSignal(flag: Boolean) {
        (activity as GroupCreationActivity).activateNextBtn(flag)
    }

    fun toSignin2() {
        (activity as GroupCreationActivity).image = profileImage
        (activity as GroupCreationActivity).profile_name = RequestBody.create(
            MediaType.parse("text/plain"),edtxt_first_login_withgroupcode2_profile_name.text.toString())
        (activity as GroupCreationActivity).profile_rank = RequestBody.create(
            MediaType.parse("text/plain"),edtxt_first_login_withgroupcode2_profile_rank.text.toString())
        //(activity as FirstLoginActivity).profile_name =  edtxt_first_login_withgroupcode2_profile_name.text.toString()
        //(activity as FirstLoginActivity).profile_rank = edtxt_first_login_withgroupcode2_profile_rank.text.toString()
    }

    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(READ_EXTERNAL_STORAGE),
                    MY_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_READ_STORAGE_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlbum()
            } else {
                activity?.finish()
            }
        }
    }

    private fun showAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val selectedImageUri: Uri = it.data!!
                    val options = BitmapFactory.Options()
                    val inputStream: InputStream? = activity?.contentResolver!!.openInputStream(selectedImageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    imageURI = getRealPathFromURI(selectedImageUri)
                    val image =
                        RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
                    profileImage = MultipartBody.Part.createFormData(
                        "images",
                        File(selectedImageUri.toString()).name,
                        image
                    )

                    Glide.with(this)
                        .load(selectedImageUri)
                        .thumbnail(0.1f)
                        .transform(CenterCrop(), CircleCrop())
                        .into(iv_first_login_withgroupcode2_editpart)
                }
            }
        }
    }

    private fun getRealPathFromURI(content: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(requireContext(), content, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }

}
