package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    var userId:Long = 0, // 사용자 식별 정보(ID)
    @ColumnInfo(name = "user_indicator")
    var userIndicator: String = "", // 사용자 식별 정보, 단말기 시리얼 번호 등 사용자 특정용 데이터
)
