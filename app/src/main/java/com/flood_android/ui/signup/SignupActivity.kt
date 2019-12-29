package com.flood_android.ui.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.flood_android.R
import com.flood_android.ui.signup.adapter.SignUpPageAdapter
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var signUpPageAdapter: SignUpPageAdapter

    private var position = 0
    private var btnFlag = false

    private val okDialog: SignupAlertDialog by lazy {
        SignupAlertDialog(this, okListener)
    }

    private val gokDialog: SignupGroupcodeMismatchDialog by lazy{
        SignupGroupcodeMismatchDialog(this,gokListener)
    }

    private val okListener = View.OnClickListener { okDialog.dismiss() }

    private val gokListener = View.OnClickListener { gokDialog.dismiss()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_signup)

        signUpPageAdapter = SignUpPageAdapter(supportFragmentManager)
        //signUpPageAdapter.addFragment(SignupFragment_1())
        //signUpPageAdapter.addFragment(SignupFragment_2())
        //signUpPageAdapter.addFragment(SignupFragment_3())
        signUpPageAdapter.addFragment(SignupFragment_4())
       //signUpPageAdapter.addFragment(SignUpFragment_5())

        vpager_signup.adapter = signUpPageAdapter
        btn_signup_next.setOnClickListener {
            if (btnFlag) {
                vpager_signup.currentItem = (position++)
            } else {
                //if(position!=3) gokDialog.show()
                //else
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
            6,
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
}
