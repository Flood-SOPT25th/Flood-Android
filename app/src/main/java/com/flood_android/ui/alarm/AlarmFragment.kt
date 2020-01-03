package com.flood_android.ui.alarm


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

        alarmRVAdapterToday.data = listOf(
            AlarmRvItem(
                profile_id = R.drawable.img_kjj,
                name = "김정재",
                action = 0,
                comment = null,
                time = "방금 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_bear,
                name = "이현주",
                action = 1,
                comment = "좋은 정보 공유 감사합니다 ^^",
                time = "10분 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_jdb,
                name = "정다비",
                action = 3,
                comment = null,
                time = "3시간 전"
            )
        )

        alarmRVToday.apply{
            adapter = alarmRVAdapterToday
          layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }

        alarmRVAdapterPrevious.data = listOf(
            AlarmRvItem(
                profile_id =R.drawable.img_kjj,
                name = "이동훈",
                action = 2,
                comment = null,
                time = "1일 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_kjj,
                name = "정서현",
                action = 1,
                comment = null,
                time = "3일 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_bear,
                name = "이현주",
                action = 1,
                comment = "우리 프로젝트에 사용하면 좋을 정보들이네요~",
                time = "1주 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_lje,
                name = "선지희",
                action = 3,
                comment = null,
                time = "2주 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_kjj,
                name = "김정재",
                action = 2,
                comment = null,
                time = "4주 전"
            ),
            AlarmRvItem(
                profile_id = R.drawable.img_ksk,
                name = "김성곤",
                action = 1,
                comment = "헉 저도 이 기사 봤었는데!! 대박이지 않나요?",
                time = "5주 전"
            )
        )

        alarmRVPrevious.apply{
            adapter = alarmRVAdapterPrevious
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

//    private fun setData(item : List<AlarmRvItem>) {
//        alarmRVAdapterToday.data = item
//        alarmRVAdapterPrevious.data = item
//        alarmRVAdapterToday.notifyDataSetChanged()
//        alarmRVAdapterPrevious.notifyDataSetChanged()
//    }

}

