package com.flood_android.ui.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.firstlogin.SigninOrgActivity
import com.flood_android.ui.signup.adapter.SignupPageAdapter
import com.flood_android.ui.signup.data.PostSignupRequest
import com.flood_android.ui.signup.data.PostSignupResponse
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var signupPageAdapter: SignupPageAdapter

    var email = ""
    var password = ""
    var name = ""
    var phone = ""
    var question = ""
    var answer = ""


    var signupInfo = PostSignupRequest(email,password,name,phone,question,answer)

    private var position = 0
    private var btnFlag = false

    private val okDialog: SignupAlertDialog by lazy {
        SignupAlertDialog(this, okListener)
    }

    private val okListener = View.OnClickListener { okDialog.dismiss() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_signup)

        signupPageAdapter = SignupPageAdapter(supportFragmentManager)
        signupPageAdapter.addFragment(SignupFragment_1())
        signupPageAdapter.addFragment(SignupFragment_2())
        signupPageAdapter.addFragment(SignupFragment_3())
        signupPageAdapter.addFragment(SignupFragment_4())
        signupPageAdapter.addFragment(SignupFragment_5())


        vpager_signup.adapter = signupPageAdapter
        //vpager_signup.offscreenPageLimit = 1
        btn_signup_next.setOnClickListener {
            if (btnFlag) {
                if (position <= 4) {
                    vpager_signup.currentItem = (position++)
                    when (position) {
                        4->{
                            /*서버통신*/
                            Log.v(email,password)
                            Log.v(name,phone)
                            Log.v(question, answer)
                            //signupInfo.copy(email,password,name, phone, question, answer)
                            postSignupResponse(signupInfo)
                        }
                    }
                } else {
                    btn_signup_next.setText("완료")
                    btn_signup_next.setTextColor(Color.parseColor("#0057ff"))
                    var intent = Intent(this, SigninOrgActivity::class.java)
                    startActivity(intent)
                }
            } else {
                okDialog.show()
            }
        }

        vpager_signup.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(p0: Int) {
                dindicator_signup.selectDot(p0)
            }
        })
        dindicator_signup.createDotPanel(
            5,
            R.drawable.circle_grey_7dp,
            R.drawable.circle_blue_7dp,
            0
        )
    }

    fun activateNextBtn(flag: Boolean) {
        btnFlag = flag
        if (flag) {
            //다음 버튼 활성화 --> 버튼 색깔 바꾸기(파랑으로 바꾸기)
            btn_signup_next.setTextColor(Color.parseColor("#0057ff"))
        } else {
            //끄기(흰색으로 바꾸기)
            btn_signup_next.setTextColor(Color.parseColor("#d1d1d1"))
        }
    }

    var fail: (Throwable) -> Unit = {
        Log.v("SignupActivity", "fail")
        Log.v("SignupActivity", it.message.toString())
        Log.v("SignupActivity", it.toString())
    }
    var temp: (PostSignupResponse) -> Unit = {
        Log.v("SignupActivity", "temp")
        Log.v("SignupActivity", it.message)
        finish()
    }

    fun postSignupResponse(ps: PostSignupRequest) {
        val postSignupResponse = ApplicationController.networkServiceUser.postSignupResponse(ps)
        postSignupResponse.safeEnqueue(fail, temp)
    }
}
