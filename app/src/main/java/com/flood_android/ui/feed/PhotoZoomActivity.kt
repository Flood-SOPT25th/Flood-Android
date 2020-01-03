package com.flood_android.ui.feed

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.flood_android.R
import com.flood_android.ui.feed.adapter.PhotoZoomStatePagerAdapter
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_photo_zoom.*


class PhotoZoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_zoom)

        initView()
    }

    private fun initView() {
        try {
            setStatusBarBlack()
            var dataList: ArrayList<String> = intent.getStringArrayListExtra("imageList")

            tv_photo_zoom_all_num.text = dataList.size.toString()

            val adapter = PhotoZoomStatePagerAdapter(this@PhotoZoomActivity, dataList)
            val vp: ViewPager = this.findViewById(com.flood_android.R.id.vp_photo_zoom)
            vp.adapter = adapter
            changeNum(vp.currentItem + 1)

            vp.setOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(arg0: Int) {}
                override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
                override fun onPageSelected(currentPage: Int) {
                    changeNum(vp.currentItem + 1)
                }
            })
            setOnClickListener()
        } catch (e: Exception) {
        }
    }

    private fun setOnClickListener() {
        btn_photo_zoom_close.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                finish()
            }
        })
    }

    fun changeNum(position: Int) {
        tv_photo_zoom_cur_num.text = position.toString()
    }

    private fun setStatusBarBlack() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.BLACK

        window.decorView.systemUiVisibility = 0
    }
}
