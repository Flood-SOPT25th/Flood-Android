package com.flood_android.ui.alarm


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.flood_android.R
import com.flood_android.ui.alarm.data.AlarmRvItem
import com.flood_android.ui.alarm.adapter.AlarmRVAdapter
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment() {

    private lateinit var alarmRVToday : RecyclerView
    private lateinit var alarmRVPrevious : RecyclerView
    private lateinit var alarmRVAdapterToday: AlarmRVAdapter
    private lateinit var alarmRVAdapterPrevious: AlarmRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAlarmList()
    }

    private fun initAlarmList() {
        alarmRVToday = rv_alarm_today
        alarmRVPrevious = rv_alarm_previous
        alarmRVAdapterToday = AlarmRVAdapter(requireContext())
        alarmRVAdapterPrevious = AlarmRVAdapter(requireContext())
        alarmRVToday.adapter = alarmRVAdapterToday
        alarmRVPrevious.adapter = alarmRVAdapterPrevious

        alarmRVAdapterToday.data = listOf(
            AlarmRvItem(
                profile_id = "",
                name = "김정재",
                action = 0,
                comment = "좋네요",
                time = "방금 전"
            ),
            AlarmRvItem(
                profile_id = "",
                name = "이현주",
                action = 1,
                comment = null,
                time = "10분 전"
            ),
            AlarmRvItem(
                profile_id = "",
                name = "정다비",
                action = 3,
                comment = null,
                time = "3시간 전"
            )
        )

        alarmRVAdapterPrevious.data = listOf(
            AlarmRvItem(
                profile_id = "",
                name = "선지희",
                action = 3,
                comment = null,
                time = "하루 전"
            ),
            AlarmRvItem(
                profile_id = "이동훈",
                name = "",
                action = 2,
                comment = null,
                time = "3일 전"
            ),
            AlarmRvItem(
                profile_id = "정서현",
                name = "",
                action = 0,
                comment = null,
                time = "일주일 전"
            )
        )
    }

//    private fun setData(item : List<AlarmRvItem>) {
//        alarmRVAdapterToday.data = item
//        alarmRVAdapterPrevious.data = item
//        alarmRVAdapterToday.notifyDataSetChanged()
//        alarmRVAdapterPrevious.notifyDataSetChanged()
//    }

}

