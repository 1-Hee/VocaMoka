package com.aiden.vokamoka.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MenuInfo (
    val menuName: String,
    val menuId:Int
) : Parcelable