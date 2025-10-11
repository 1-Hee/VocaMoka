package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.Expression

@Dao
interface ExpressionDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertExpression(entity: Expression) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllExpression(vararg entity: Expression)

    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM expression")
    fun selectExpressionList(): List<Expression>

    @Query("SELECT * FROM expression WHERE exp_id = :entityId")
    fun selectExpression(entityId: Long): Expression

    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateExpression(entity: Expression)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteExpression(entity: Expression)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM expression")
    fun deleteAll()

}