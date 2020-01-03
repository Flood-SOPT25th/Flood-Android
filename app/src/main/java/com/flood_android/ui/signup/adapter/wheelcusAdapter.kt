package com.flood_android.ui.signup.adapter

import com.super_rabbit.wheel_picker.WheelAdapter

class wheelcusAdapter : WheelAdapter {

    val arr = arrayOf(
        "아버지의 성함은?", "어머니의 성함은?",
        "기억에 남는 추억의 장소는?", "자신의 인생 좌우명은?",
        "가장 기억에 남는 선생님 성함은?",
        "받았던 선물 중 가장 기억에 남는 선물은?",
        "유년시절 가장 생각나는 친구 이름은?",
        "인상깊게 읽은 책 이름은?", "자신이 첫 번째로 존경하는 인물은?",
        "내가 좋아하는 캐릭터는?"
    )

    override fun getMaxIndex(): Int {
        return arr.size-1
    }

    override fun getMinIndex(): Int {
        return 0
    }

    override fun getPosition(vale: String): Int {
        return getPosition(vale)
    }

    override fun getValue(position: Int): String {
        return arr[position]
    }

    override fun getTextWithMaximumLength(): String {
        return getTextWithMaximumLength()
    }

}