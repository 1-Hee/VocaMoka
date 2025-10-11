package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.StudySchedule

@Dao
interface StudyScheduleDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertStudySchedule(entity: StudySchedule) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllStudySchedule(vararg entity: StudySchedule)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM study_schedule")
    fun selectStudyScheduleList(): List<StudySchedule>

    @Query("SELECT * FROM study_schedule WHERE schedule_id = :entityId")
    fun selectStudySchedule(entityId: Long): StudySchedule

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateStudySchedule(entity: StudySchedule)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteStudySchedule(entity: StudySchedule)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM study_schedule")
    fun deleteAll()

}