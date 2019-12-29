package com.flood_android.ui.signup.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SignupViewPager (ctx: Context, attr : AttributeSet) : ViewPager(ctx, attr) {

    // 스와이프에 따른 ViewPager의 이동을 막는다.
    override fun onTouchEvent(ev: MotionEvent?): Boolean = false
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false
}