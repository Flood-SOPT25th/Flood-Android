package com.flood_android.ui.firstlogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.firstlogin.post.PostCreateOrgReq
import com.flood_android.ui.firstlogin.post.PostCreateOrgResponse
import com.flood_android.ui.firstlogin.post.PostProfileSetResponse
import com.flood_android.ui.login.LoginAlertDialog
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.signup.SignupAlertDialog
import com.flood_android.ui.signup.adapter.SignupPageAdapter
import com.flood_android.util.GlobalData
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_group_creation.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class GroupCreationActivity : AppCompatActivity() {
    lateinit var firstGroupCreationPageAdapter: SignupPageAdapter
    private var position = 0
    private var btnFlag = false
    private var serverFlag = true

    var name=""
    var phone = ""
    var department = ""
    var groupcode = ""
    var categoryList = mutableListOf<String>()
    var context_type_general = "application/json"
    var context_type_profile_set = "application/x-www-form-urlencoded"
    var authorization1 = ""
    var authorization2 = ""
    var groupCRInfo = PostCreateOrgReq(name,phone,department,categoryList)

    private val exceptionHandlingAlertDialog by lazy {
        LoginAlertDialog()
    }


    private val groupcodeMismatchDialog: GroupcodeMismatchDialog by lazy {
        GroupcodeMismatchDialog(this, groupcodeMismatchListener)
    }
    private val groupcodeMismatchListener =
        View.OnClickListener { groupcodeMismatchDialog.dismiss() }

    private val okDialog: SignupAlertDialog by lazy {
        SignupAlertDialog(this, okListener)
    }

    private val okListener = View.OnClickListener { okDialog.dismiss() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_creation)

        firstGroupCreationPageAdapter = SignupPageAdapter(supportFragmentManager)

        //firstGroupCreationPageAdapter.addFragment(GroupCreationFragment1())
        //firstGroupCreationPageAdapter.addFragment(GroupCreationFragment2())
        //firstGroupCreationPageAdapter.addFragment(GroupCreationFragment3())
        firstGroupCreationPageAdapter.addFragment(GroupCreationFragment4())

        vpager_group_creation.adapter = firstGroupCreationPageAdapter

        vpager_group_creation.offscreenPageLimit = 1

        btn_group_creation_next.setOnClickListener {

            if (btnFlag) {
                when(position){
                    2 -> {
                        serverFlag = false
                        authorization1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imtha2FvNUBnbWFpbC5jb20iLCJuYW1lIjoi7Lm07Lm07JikNSIsImlhdCI6MTU3ODA1NjE3MCwiZXhwIjoxNTgwNjQ4MTcwLCJpc3MiOiJGbG9vZFNlcnZlciJ9.OIMTqJtYjpsz3xM5EItunHCqTmncW0ac9xilPnkdVc0"
                        //authorization1 = SharedPreferenceController.getAuthorization(this!!)!!
                        postCrOrg(context_type_general,authorization1,groupCRInfo)
                        Log.v("Jihee1",context_type_general)
                        Log.v("Jihee1",authorization1)
                        Log.v("Jihee1",groupCRInfo.toString())

                    }
                    3->{
                        Log.v("Jihee2",serverFlag.toString())
                        serverFlag = true
                    }
                    4 -> {
                        serverFlag = false
                        authorization2="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imtha2FvNUBnbWFpbC5jb20iLCJuYW1lIjoi7Lm07Lm07JikNSIsImlhdCI6MTU3ODA1NjE3MCwiZXhwIjoxNTgwNjQ4MTcwLCJpc3MiOiJGbG9vZFNlcnZlciJ9.OIMTqJtYjpsz3xM5EItunHCqTmncW0ac9xilPnkdVc0"
                        //authorization2 = SharedPreferenceController.getAuthorization(this!!)!!
                        Log.v("ㅁㅁㅁㅁ", profile_name.toString())
                        Log.v("ㅁㅁㅁㅁ", profile_rank.toString())
                        postProfSetRes(context_type_profile_set,authorization2,image,profile_name,profile_rank)
                        //intent.putExtra("groupcode", bundleOf(("jihee";groupcode))
                    }
                }
              if(serverFlag)
                    vpager_group_creation.currentItem = (position++)
            } else {
                okDialog.show()
            }
        }

        vpager_group_creation.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(p0: Int) {
                dindicator_group_creation.selectDot(p0)
            }
        })
        dindicator_group_creation.createDotPanel(
            4,
            R.drawable.circle_grey_7dp,
            R.drawable.circle_blue_7dp,
            0
        )

    }


    fun activateNextBtn(flag: Boolean) {
        btnFlag = flag
        if (flag) {
            //다음 버튼 활성화 --> 버튼 색깔 바꾸기(파랑으로 바꾸기)
            if(vpager_group_creation.currentItem == 3)
                btn_group_creation_next.text = "완료"
            btn_group_creation_next.setTextColor(Color.parseColor("#0057ff"))
        } else {
            //끄기(흰색으로 바꾸기)
            btn_group_creation_next.setTextColor(Color.parseColor("#d1d1d1"))
        }
    }


    // 조직 생성코드 받기 위해 앞 2페이지에서 수집한 데이터 서버에게 보냄

    var fail1: (Throwable) -> Unit = {
        Log.v("GroupCreationActivity", "fail")
        Log.v("GroupCreationActivity", it.message.toString())
        Log.v("GroupCreationActivity", it.toString())
    }
    var temp1: (PostCreateOrgResponse) -> Unit = {
        Log.v("GroupCreationActivity", "temp")
        Log.v("GroupCreationActivity", it.message)

        if (it.message == "조직 생성 완료"){
            Log.v("Jihee","finish!")
            groupcode = it.code
            GlobalData.gCode = it.code
            Log.v(groupcode,"groupcode")
            serverFlag = true
            //findViewById<TextView>(R.id.edtxt_first_login_withoutgroupcode3_copycode).text=groupcode
            Log.v("Jihee1","bundle")
            var bundle = Bundle()
            bundle.putString("GCODE",groupcode)
            Log.v("GCODE",groupcode)
            supportFragmentManager.findFragmentById(R.id.cl_firstlogin_3rd)?.arguments = bundle
            Log.v("bundle",bundle.toString())
            //fr3.arguments=bundle
            //Log.v("Jihee1",fr3.arguments.toString())
            //vpager_group_creation.currentItem = (position++)
        }
        if (it.message == "모든 정보를 입력해주세요."){
            Log.v("jihee","no info")
            exceptionHandlingAlertDialog.show(supportFragmentManager, "Info Alert Dialog")
            GlobalData.loginDialogMessage = "모든 정보를 입력해주세요."
            serverFlag = false
        }
        if (it.message == "이미 조직이 존재합니다."){
            Log.v("jihee","existed")
            exceptionHandlingAlertDialog.show(supportFragmentManager, "existed org Alert Dialog")
            GlobalData.loginDialogMessage = "이미 존재하는 조직입니다. 그룹코드를 조회해주세요."
            serverFlag = false
            //val intent = Intent(this@GroupCreationActivity, SigninOrgActivity::class.java)
            //startActivity(intent)
            //finish()
        }
    }


    fun postCrOrg(
        context_type : String, authorization : String, pcr: PostCreateOrgReq) {
        val postCreateOrgResponse
                = ApplicationController.networkServiceUser.postCreateOrganization(context_type, authorization, pcr)
        postCreateOrgResponse.safeEnqueue(fail1, temp1)
    }

    // FirstLogin Activity와 동일한 프로세스 : 개인 프로필 설정

    var image: MultipartBody.Part? = null
    lateinit var profile_name : RequestBody
    lateinit var profile_rank: RequestBody


    var fail2: (Throwable) -> Unit = {
        Log.v("GroupCreationActivity", it.toString())
    }
    var temp2: (PostProfileSetResponse) -> Unit = {
        Log.v("GroupcreationActivity", it.message)
        if(it.message == "그룹 가입 성공") {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else if(it.message == "유효하지 않는 그룹 코드입니다.") {
            exceptionHandlingAlertDialog.show(
                supportFragmentManager,
                "invalid group code"
            )
            GlobalData.loginDialogMessage = "유효하지 않는 그룹 코드입니다."
        }
        else if(it.message == "server error"){
            exceptionHandlingAlertDialog.show(
                supportFragmentManager,
                "Server Error Dialog"
            )
            GlobalData.loginDialogMessage = "서버 에러입니다."
        }
    }

    fun postProfSetRes(
        context_type: String,
        authorization: String,
        image: MultipartBody.Part?,
        profile_name : RequestBody,
        profile_rank: RequestBody
    ){
        val postProfileSetResponse = ApplicationController.networkServiceUser.postProfileSetting(
            context_type,authorization,image,profile_name,profile_rank
        )
        postProfileSetResponse.safeEnqueue(fail2,temp2)
    }
}

