package com.flood_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.flood_android.MypageFragment
import com.flood_android.R
import com.flood_android.ui.alarm.AlarmFragment
import com.flood_android.ui.company.CompanyFragment
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.post.PostActivity
import com.flood_android.ui.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(FeedFragment())

        iv_main_tab_feed.setOnClickListener{
            replaceFragment(FeedFragment())
        }
        iv_main_tab_company.setOnClickListener{
            replaceFragment(CompanyFragment())
        }
        iv_main_tab_write.setOnClickListener{
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
        }
        iv_main_tab_alarm.setOnClickListener{
            replaceFragment(AlarmFragment())
        }
        iv_main_tab_mypage.setOnClickListener{
            replaceFragment(MypageFragment())
        }
    }

    fun addFragment(fragment: Fragment){
        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        // 이 아이디 자리에, 어떤 프래그먼트를 넣어주겠다.
        transaction.add(R.id.fl_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment){
        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        // 이 아이디 자리에, 어떤 프래그먼트를 넣어주겠다.
        transaction.replace(R.id.fl_main, fragment)
        transaction.commit()
    }
}
