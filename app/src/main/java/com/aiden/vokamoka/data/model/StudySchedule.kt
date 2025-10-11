package com.aiden.vokamoka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "study_schedule",
    foreignKeys = [
        ForeignKey(
            entity = UserInfo::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("fk_user_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordPool::class,
            parentColumns = arrayOf("wp_inex"),
            childColumns = arrayOf("fk_wp_inex"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StudySchedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "schedule_id")
    var scheduleId:Long = 0, // 고유 ID
    @ColumnInfo(name = "fk_user_id")
    val fkUserId: Long, // 외래키, 사용자 식별 정보(ID)
    @ColumnInfo(name = "fk_wp_inex")
    val fkWpInex: Long, // 외래키, 단어 샘(Pool) 식별 정보(ID)
    @ColumnInfo(name = "scheduled_date")
    var scheduledDate: Date = Date(), // 단어 학습 일자
    @ColumnInfo(name = "status")
    var status: Int = 0, // 단어 학습 상태
    @ColumnInfo(name = "next_review_date")
    var nextReviewDate: Date? = null, // 단어 복습 일자
    @ColumnInfo(name = "repetition_count")
    var repetitionCount: Int = 0, // 단어 학습 횟수
    @ColumnInfo(name = "created_at")
    var createdAt: Date = Date(), // 생성 일자
    @ColumnInfo(name = "updated_at")
    var updatedAt: Date = Date(), // 수정 일자
)
