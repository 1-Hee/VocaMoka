package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "expression",
    foreignKeys = [
        ForeignKey(
            entity = Concept::class,
            parentColumns = arrayOf("concept_id"),
            childColumns = arrayOf("fk_concept_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Language::class,
            parentColumns = arrayOf("lang_code"),
            childColumns = arrayOf("fk_lang_code"),
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Expression(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exp_id")
    var expId:Long = 0, // 고유 ID
    @ColumnInfo(name = "fk_concept_id")
    val fkConceptId: Long, // 외래키, 개념 식별 ID
    @ColumnInfo(name = "fk_lang_code")
    val fkLangCode: Long, // 외래키, 언어 식별 ID
    @ColumnInfo(name = "word_text")
    var wordText: String = "", // 언어 단어(Word)
    @ColumnInfo(name = "note")
    var note: String = "", // 단어 설명
)
