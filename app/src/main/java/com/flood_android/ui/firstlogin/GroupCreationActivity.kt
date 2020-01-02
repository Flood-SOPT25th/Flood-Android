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
import androidx.viewpager.widget.ViewPager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.feed.FeedCategoryFragment
import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.signup.SignupAlertDialog
import com.flood_android.ui.signup.adapter.SignupPageAdapter
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.activity_group_creation.*
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode2.*


class GroupCreationActivity : AppCompatActivity() {
    lateinit var firstGroupCreationPageAdapter: SignupPageAdapter
    private var position = 0
    private var btnFlag = false

    lateinit var getCategoryDataList: ArrayList<String>
    val networkService: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }


    private var fr1 = FirstLoginFragmentWithoutGroupcode1()
    private var fr2 = FirstLoginFragmentWithoutGroupcode2()
    private var fr3 = FirstLoginFragmentWithoutGroupcode3()
    private var fr4 = FirstLoginFragmentWithoutGroupcode4()

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

        firstGroupCreationPageAdapter.addFragment(fr1)
        firstGroupCreationPageAdapter.addFragment(fr2)
        firstGroupCreationPageAdapter.addFragment(fr3)
        firstGroupCreationPageAdapter.addFragment(fr4)


        vpager_group_creation.adapter = firstGroupCreationPageAdapter

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
        if (position == 3) btn_group_creation_next.text = "완료"

        btn_group_creation_next.setOnClickListener {
            if (btnFlag) {
                if (position <= 3)
                        vpager_group_creation.currentItem = (position++)
                else {
                    var intent2 = Intent(this, MainActivity::class.java)
                    startActivity(intent2)
                }
            } else {
                okDialog.show()
            }
        }
    }


    fun activateNextBtn(flag: Boolean) {
        btnFlag = flag
        if (flag) {
            //다음 버튼 활성화 --> 버튼 색깔 바꾸기(파랑으로 바꾸기)
            btn_group_creation_next.setTextColor(Color.parseColor("#0057ff"))
        } else {
            //끄기(흰색으로 바꾸기)
            btn_group_creation_next.setTextColor(Color.parseColor("#d1d1d1"))
        }
    }

}

