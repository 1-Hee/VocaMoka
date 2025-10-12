package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.WordPool

@Dao
interface WordPoolDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertWordPool(entity: WordPool) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllWordPool(vararg entity: WordPool)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM word_pool")
    fun selectWordPoolList(): List<WordPool>

    @Query("SELECT * FROM word_pool WHERE wp_inex = :entityId")
    fun selectWordPool(entityId: Long): WordPool

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateWordPool(entity: WordPool)

    // * ------------------------
    // *    Delete
    // * ------------------------

    @Query("DELETE FROM word_pool WHERE wp_inex = :wpIndex")
    fun deleteWordPool(wpIndex: Long)

    @Delete
    fun deleteWordPool(entity: WordPool)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM word_pool")
    fun deleteAll()

}