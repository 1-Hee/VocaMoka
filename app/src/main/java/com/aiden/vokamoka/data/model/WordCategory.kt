package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_category")
data class WordCategory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var categoryId:Long = 0, // 고유 ID
    @ColumnInfo(name = "name")
    var name: String = "", // 카테고리 명 (eg. phr, n, adj ... )
)

