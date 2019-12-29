package com.flood_android.ui.alarm.data

data class AlarmRvItem(
    val profile_id : String,
    val name : String,
    val action : Int,
    val comment : String?,
    val time : String
)