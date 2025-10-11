package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
data class Language(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lang_code")
    var langCode:Long = 0, // 고유 ID
    @ColumnInfo(name = "name")
    var name: String = "", // 언어 명 (eg. ko, en, ... )
)
