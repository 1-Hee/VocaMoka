package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "word_pool",
    foreignKeys = [
        ForeignKey(
            entity = UserInfo::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("fk_user_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Expression::class,
            parentColumns = arrayOf("exp_id"),
            childColumns = arrayOf("origin_exp_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Expression::class,
            parentColumns = arrayOf("exp_id"),
            childColumns = arrayOf("target_exp_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordPool(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wp_inex")
    var wpInex:Long = 0, // 고유 ID
    @ColumnInfo(name = "fk_user_id")
    var fkUserId: Long? = null, // 외래키, 사용자 식별 정보(ID)
    @ColumnInfo(name = "origin_exp_id")
    var originExpId: Long, // 외래키, 단어 표현 식별자(ID) (= 암기할 단어)
    @ColumnInfo(name = "target_exp_id")
    var targetExpId: Long, // 외래키, 단어 표현 식별자(ID) (= 뜻을 비교할 단어)
    @ColumnInfo(name = "usr_difficulty")
    var usrDifficulty: Int = 0, // 사용자 난이도(Difficulty)
    @ColumnInfo(name = "date_created")
    var dateCreated: Date = Date(), // 단어 생성 날짜
    @ColumnInfo(name = "date_edited")
    var dateEdited: Date = Date(), // 단어 수정 날짜
)
