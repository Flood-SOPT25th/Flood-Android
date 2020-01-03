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
import com.flood_android.ui.firstlogin.post.PostProfileSetResponse
import com.flood_android.ui.firstlogin.post.PostSignInOrgReq
import com.flood_android.ui.firstlogin.post.PostSignInOrgResponse
import com.flood_android.ui.login.LoginAlertDialog
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.signup.SignupAlertDialog
import com.flood_android.ui.signup.adapter.SignupPageAdapter
import com.flood_android.util.GlobalData
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_first_login.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class SigninOrgActivity : AppCompatActivity() {
    lateinit var firstLoginPageAdapter: SignupPageAdapter
    private var position = 0
    private var btnFlag = false
    private var serverFlag = true
    var groupcode = ""


    private val okDialog: SignupAlertDialog by lazy {
        SignupAlertDialog(this, okListener)
    }
    private val okListener = View.OnClickListener { okDialog.dismiss() }
    private val exceptionHandlingAlertDialog by lazy {
        LoginAlertDialog()
    }

    private val groupcodeMismatchDialog: GroupcodeMismatchDialog by lazy {
        GroupcodeMismatchDialog(this, groupcodeMismatchListener)
    }
    private val groupcodeMismatchListener =
        View.OnClickListener { groupcodeMismatchDialog.dismiss() }

    //private val fr1 = FirstLoginFragmentWithGroupcode1()
    //private val fr2 = FirstLoginFragmentWithGroupcode2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)

        firstLoginPageAdapter = SignupPageAdapter(supportFragmentManager)
        firstLoginPageAdapter.addFragment(SigninOrgFragment1())
        firstLoginPageAdapter.addFragment(SigninOrgFragment2())

        vpager_signin_org.adapter = firstLoginPageAdapter

        btn_first_login_next.setOnClickListener {
            if (btnFlag) {
                when(position){
                    1-> {
                        authorization1="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inllb25naHVuMDMyN0BnbWFpbC5jb20iLCJuYW1lIjoi7LWc7JiB7ZuIIiwiaWF0IjoxNTc4MDQyMTQ1LCJleHAiOjE1ODA2MzQxNDUsImlzcyI6IkZsb29kU2VydmVyIn0.YA5cNL38T5tyyRAj1qvQBr-O3RDwufI0QwSo3Wwjyn4"
                        //authorization1 = SharedPreferenceController.getAuthorization(this)!!
                        postSignInOrg(context_type_signin,authorization1,PostSignInOrgReq(groupcode))
                    }
                    2-> {
                        Log.v("Jihee","3")
                        authorization2="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inllb25naHVuMDMyN0BnbWFpbC5jb20iLCJuYW1lIjoi7LWc7JiB7ZuIIiwiaWF0IjoxNTc4MDQyMTQ1LCJleHAiOjE1ODA2MzQxNDUsImlzcyI6IkZsb29kU2VydmVyIn0.YA5cNL38T5tyyRAj1qvQBr-O3RDwufI0QwSo3Wwjyn4\""
                        //authorization2 = SharedPreferenceController.getAuthorization(this)!!
                        //btn_first_login_next.text = "완료"
                        //var pname = RequestBody.create(
                        //    MediaType.parse("text/plain"),profile_name)
                        //var prank = RequestBody.create(
                         //   MediaType.parse("text/plain"),profile_rank)
                        Log.v("Jihee","들어와?")
                        postProfSetRes(context_type_profile_set,authorization2,image,profile_name,profile_rank)
                        Log.v("Jihee","서버통신")

                    }
                }

                vpager_signin_org.currentItem = position++

            } else {
                okDialog.show()
            }
        }

        vpager_signin_org.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(p0: Int) {
                dindicator_first_login.selectDot(p0)
            }
        })
        dindicator_first_login.createDotPanel(
            2,
            R.drawable.circle_grey_7dp,
            R.drawable.circle_blue_7dp,
            0
        )
    }

    fun activateNextBtn(flag: Boolean) {
        btnFlag = flag
        if (flag) {
            //다음 버튼 활성화 --> 버튼 색깔 바꾸기(파랑으로 바꾸기)
            if(vpager_signin_org.currentItem == 1)
                btn_first_login_next.text = "완료"
            btn_first_login_next.setTextColor(Color.parseColor("#0057ff"))
        } else {
            //끄기(흰색으로 바꾸기)
            btn_first_login_next.setTextColor(Color.parseColor("#d1d1d1"))
        }
    }

    /**Server related settings**/

    var context_type_signin = "application/json"
    var context_type_profile_set = "application/x-www-form-urlencoded"
    var authorization1 = ""
    var authorization2 = ""
    var image: MultipartBody.Part? = null
    //var profile_name = ""
    //var profile_rank = ""

    lateinit var profile_name : RequestBody
    lateinit var profile_rank: RequestBody


    var fail1: (Throwable) -> Unit = {
        Log.v("FirstLoginActivity", it.toString())
    }
    var temp1: (PostSignInOrgResponse) -> Unit = {
        Log.v("FirstLoginActivity", it.message)
        if(it.message == "유효하지 않는 그룹 코드입니다.") {
            groupcodeMismatchDialog.show()
            serverFlag = false
        }
        else
            serverFlag = true
    }

    var fail2: (Throwable) -> Unit = {
        Log.v("FirstLoginActivity", it.toString())
    }
    var temp2: (PostProfileSetResponse) -> Unit = {
        Log.v("FirstLoginActivity", it.message)
        if(it.message == "그룹 가입 성공") {
            Log.v("Jihee",it.message)
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

    fun postSignInOrg(
        context_type : String,
        authorization : String,
        pSin: PostSignInOrgReq
    ){
        val postSignInOrgReq = ApplicationController.networkServiceUser.postSignInOrganization(
            context_type,authorization,pSin
        )
        postSignInOrgReq.safeEnqueue(fail1,temp1)
    }

    fun postProfSetRes(
        context_type: String,
        authorization: String,
        image: MultipartBody.Part?,
        profile_name : RequestBody,
        profile_rank: RequestBody
    ){
        var postProfileSetResponse = ApplicationController.networkServiceUser.postProfileSetting(
            context_type,authorization,image,profile_name,profile_rank
        )
        postProfileSetResponse.safeEnqueue(fail2,temp2)
    }
}
