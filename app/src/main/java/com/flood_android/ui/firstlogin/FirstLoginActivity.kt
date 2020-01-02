package com.flood_android.ui.firstlogin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.flood_android.R
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.signup.SignupAlertDialog
import com.flood_android.ui.signup.adapter.SignupPageAdapter
import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode1.*
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode1.view.*
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode2.*


class FirstLoginActivity : AppCompatActivity() {
    lateinit var firstLoginPageAdapter: SignupPageAdapter
    private var position = 0
    private var btnFlag = false
    private var toastFlag = true
    //private var serverFlag = false
    private var serverFlag = true
    private lateinit var groupcode: String

    private val okDialog: SignupAlertDialog by lazy {
        SignupAlertDialog(this, okListener)
    }

    private val okListener = View.OnClickListener { okDialog.dismiss() }

    private val groupcodeMismatchDialog: GroupcodeMismatchDialog by lazy {
        GroupcodeMismatchDialog(this, groupcodeMismatchListener)
    }
    private val groupcodeMismatchListener =
        View.OnClickListener { groupcodeMismatchDialog.dismiss() }

    private val fr1 = FirstLoginFragmentWithGroupcode1()
    private val fr2 = FirstLoginFragmentWithGroupcode2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)

        firstLoginPageAdapter = SignupPageAdapter(supportFragmentManager)
        firstLoginPageAdapter.addFragment(fr1)
        firstLoginPageAdapter.addFragment(fr2)
        if (position == 1) btn_first_login_next.text = "완료"

        vpager_first_login.adapter = firstLoginPageAdapter

        btn_first_login_next.setOnClickListener {
            if (btnFlag) {
                if (position <= 1) {
                    //여기서 서버통신 --> 통신 안되면 dialog 띄울 것
                    //groupcode = supportFragmentManager.findFragmentById(R.id.edtxt_first_login_withgroupcode)?.getText().toString()
                    //groupcode = fr1.edtxt_first_login_withgroupcode.text.toString()
                    if (serverFlag)
                        vpager_first_login.currentItem = (position++)
                    else
                        groupcodeMismatchDialog.show()
                } else {
                    var intent2 = Intent(this, MainActivity::class.java)
                    startActivity(intent2)
                }
            } else {
                okDialog.show()
            }
        }

        vpager_first_login.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
            btn_first_login_next.setTextColor(Color.parseColor("#0057ff"))
        } else {
            //끄기(흰색으로 바꾸기)
            btn_first_login_next.setTextColor(Color.parseColor("#d1d1d1"))
        }
    }

    fun getValueFromFrag1(s: String) {
        groupcode = s
    }

    fun convertToastFlag(flag: Boolean) {
        toastFlag = flag
    }
}
