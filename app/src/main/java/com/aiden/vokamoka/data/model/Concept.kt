package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "concept",
    foreignKeys = [
        ForeignKey(
            entity = WordCategory::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("fk_category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Concept (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "concept_id")
    var conceptId:Long = 0, // 고유 ID
    @ColumnInfo(name = "description")
    var description: String = "", // 개념 설명 ( 언어 상관 x, 사과 or apple, 바나나 or banana )
    @ColumnInfo(name = "fk_category_id")
    val fkCategoryId: Long, // 외래키, 개념의 카테고리를 구분
)
