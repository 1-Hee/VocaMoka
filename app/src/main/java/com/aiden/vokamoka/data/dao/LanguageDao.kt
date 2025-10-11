package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.Language

@Dao
interface LanguageDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertLanguage(entity: Language) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllLanguage(vararg entity: Language)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM language")
    fun selectLanguageList(): List<Language>

    @Query("SELECT * FROM language WHERE lang_code = :entityId")
    fun selectLanguage(entityId: Long): Language

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateLanguage(entity: Language)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteLanguage(entity: Language)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM language")
    fun deleteAll()

}