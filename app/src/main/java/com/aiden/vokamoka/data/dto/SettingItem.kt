package com.aiden.vokamoka.data.dto

data class SettingItem(
    val name:String,
    var value:String,
    var dangerFlag:Boolean = false
)
