package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.Concept

@Dao
interface ConceptDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertConcept(entity: Concept) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllConcept(vararg entity: Concept)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM concept")
    fun selectConceptList(): List<Concept>

    @Query("SELECT * FROM concept WHERE concept_id = :entityId")
    fun selectConcept(entityId: Long): Concept

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateConcept(entity: Concept)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteConcept(entity: Concept)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM concept")
    fun deleteAll()
}