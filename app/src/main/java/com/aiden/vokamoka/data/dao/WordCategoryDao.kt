package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.WordCategory

@Dao
interface WordCategoryDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertWordCategory(entity: WordCategory) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllWordCategory(vararg entity: WordCategory)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM word_category")
    fun selectWordCategoryList(): List<WordCategory>

    @Query("SELECT * FROM word_category WHERE category_id = :entityId")
    fun selectWordCategory(entityId: Long): WordCategory

    @Query("""
    SELECT COALESCE((
        SELECT category_id
        FROM word_category
        WHERE name LIKE '%' || :idValue || '%'
        LIMIT 1
        ), -1)
    """)
    fun searchCategoryId(idValue: String): Long

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateWordCategory(entity: WordCategory)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteWordCategory(entity: WordCategory)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM word_category")
    fun deleteAll()

}