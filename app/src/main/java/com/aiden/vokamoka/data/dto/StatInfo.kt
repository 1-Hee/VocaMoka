package com.aiden.vokamoka.data.dto

import android.graphics.drawable.Drawable

data class StatInfo(
    val name:String,
    val guideText:String = "",
    val iconDrawable: Drawable? = null,
    val statResId:Int = -1
)
