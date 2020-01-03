package com.flood_android.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_alarm_set.*

class AlarmSetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_set)

        if (alarm_switch.isChecked == false){
            txt_comment.visibility = View.INVISIBLE
            comment_switch.visibility = View.INVISIBLE
            txt_tag.visibility = View.INVISIBLE
            tag_switch.visibility = View.INVISIBLE
        }
        else {
            
        }
    }
}
