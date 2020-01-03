package com.flood_android.ui.alarm.data

data class AlarmRvItem(
    val profile_id : Int,
    val name : String,
    val action : Int,
    val comment : String?,
    val time : String
)