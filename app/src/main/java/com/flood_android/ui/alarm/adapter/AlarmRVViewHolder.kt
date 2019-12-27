package com.flood_android.ui.alarm.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.network.Jihee.AlarmRvItem

class AlarmRVViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val ivAlarmUser: ImageView = view.findViewById(R.id.ivcircular_rv_item_alarm_user)
    private val tvAlarmAction: TextView = view.findViewById(R.id.tv_rv_item_alarm_action)
    private val tvAlarmUserName: TextView = view.findViewById(R.id.tv_rv_item_alarm_username)
    private val tvAlarmComment: TextView = view.findViewById(R.id.tv_rv_item_alarm_comment)
    private val tvAlarmTime: TextView = view.findViewById(R.id.tv_rv_item_alarm_time)
    private val tvAlarmRecent : ImageView = view.findViewById(R.id.iv_rv_item_alarm_recent)

    fun bind(data : AlarmRvItem){
        Glide.with(ivAlarmUser.context).load(data.profile_id).into(ivAlarmUser)
        tvAlarmUserName.text = data.name
        tvAlarmTime.text = data.time
        tvAlarmComment.text = ": "+data?.comment

        when(data.action){
            0 -> tvAlarmAction.setText("님이 게시물에 댓글을 남겼습니다.")
            1 -> tvAlarmAction.setText("님이 회원님의 포스트를 좋아합니다.")
            2 -> tvAlarmAction.setText("님이 새로운 멤버로 들어왔습니다.")
            3 -> tvAlarmAction.setText("님이 게시물에 회원님을 태그했습니다.")
        }

        tvAlarmComment?.isVisible = true
        if(tvAlarmTime.text == "방금 전") tvAlarmRecent.isVisible = true

        /*if(tvAlarmTime.text == "방금 전")
            tvAlarmRecent.setImageResource(R.drawable.circle_blue_4dp)*/
    }

}