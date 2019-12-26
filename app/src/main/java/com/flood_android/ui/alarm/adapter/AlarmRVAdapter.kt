package com.flood_android.ui.alarm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.network.Jihee.AlarmRvItem

class AlarmRVAdapter(private val context: Context) : RecyclerView.Adapter<AlarmRVViewHolder>(){
    var data = listOf<AlarmRvItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmRVViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_alarm, parent, false)
        return AlarmRVViewHolder(view)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: AlarmRVViewHolder, position: Int) {
        holder.bind(data[position])
    }


}