package com.flood_android.ui.signup.adapter

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout

class DotIndicator:LinearLayout {
    private var mContext: Context? = null
    private var mDefaultDot: Int = 0
    private var mSelectDot : Int = 0

    private var imageDot: MutableList<ImageView> = mutableListOf()

    private val temp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8f,resources.displayMetrics)

    constructor(context:Context) : super(context){mContext=context}
    constructor(context:Context, attrs: AttributeSet):super(context,attrs){mContext=context}

    fun createDotPanel(count:Int, defaultDot:Int, selectDot:Int, position:Int){
        this.removeAllViews()

        mDefaultDot=defaultDot
        mSelectDot=selectDot

        for(i in 0 until count){
            imageDot.add(ImageView(mContext).apply{setPadding(temp.toInt(),0,temp.toInt(),0)})
            this.addView(imageDot[i])
        }

        selectDot(position)
    }

    fun selectDot(position: Int){
        for(i in imageDot.indices){
            if(i==position){
                imageDot[i].setImageResource(mSelectDot)
            }else{
                imageDot[i].setImageResource(mDefaultDot)
            }
        }
    }
}