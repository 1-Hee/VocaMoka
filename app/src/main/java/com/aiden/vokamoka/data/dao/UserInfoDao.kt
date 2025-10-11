package com.aiden.vokamoka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aiden.vokamoka.data.model.UserInfo

@Dao
interface UserInfoDao {

    // * ------------------------
    // *    Create
    // * ------------------------
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertUserInfo(entity: UserInfo) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAllUserInfo(vararg entity: UserInfo)


    // * ------------------------
    // *    Read
    // * ------------------------
    @Query("SELECT * FROM user_info")
    fun selectUserInfoList(): List<UserInfo>

    @Query("SELECT * FROM user_info WHERE user_id = :entityId")
    fun selectUserInfo(entityId: Long): UserInfo


    // * ------------------------
    // *    Update
    // * ------------------------
    @Update
    fun updateUserInfo(entity: UserInfo)

    // * ------------------------
    // *    Delete
    // * ------------------------
    @Delete
    fun deleteUserInfo(entity: UserInfo)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM user_info")
    fun deleteAll()

}

/*

@Dao
interface AccountInfoDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun addAccountInfo(accountInfo : AccountInfo) : Long

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun addAllAccountInfo(vararg accountInfo : AccountInfo)

    // Read
    @Query("""
        SELECT
            ai.account_id, ai.fk_info_id,
            ai.user_account, ai.user_password,
            ai.ac_created_at, ai.official_url
        FROM identity_info ii
        JOIN account_info ai
        ON ii.info_id = ai.fk_info_id
        WHERE ii.status = 0
        ORDER BY ii.created_at DESC;
    """)
    fun readAccountInfoList(): List<AccountInfo>

    @Query("""
        SELECT
            ii.info_id, ii.fk_user_id, ii.info_type,
            ii.provider_name, ii.created_at, ii.updated_at, ii.memo,
            ii.tag_color, ii.status,
            ai.account_id, ai.fk_info_id,
            ai.user_account, ai.user_password,
            ai.ac_created_at, ai.official_url
        FROM identity_info ii
        JOIN account_info ai
        ON ii.info_id = ai.fk_info_id
        WHERE ii.status = 0
        ORDER BY ii.created_at DESC;
    """)
    fun readAllCountInfoList(): List<IdAccountInfo>

    @Query("""
        SELECT
            ii.info_id, ii.fk_user_id, ii.info_type,
            ii.provider_name, ii.created_at, ii.updated_at, ii.memo,
            ii.tag_color, ii.status,
            ai.account_id, ai.fk_info_id,
            ai.user_account, ai.user_password,
            ai.ac_created_at, ai.official_url
        FROM identity_info ii
        JOIN account_info ai
        ON ii.info_id = ai.fk_info_id
        WHERE ii.info_id = :infoId AND ii.status = 0;
    """)
    fun readIdAccountInfoById(infoId:Long): IdAccountInfo


    // Update
    @Query("""
        UPDATE account_info SET
        fk_info_id = :fkInfoId,
        user_account = :userAccount,
        user_password = :userPassword,
        ac_created_at = :acCreatedAt,
        official_url = :officialUrl
        WHERE account_id = :accountId
    """)
    fun modifyAccountInfo(
        accountId:Long, fkInfoId:Long?,
        userAccount:String, userPassword: String,
        acCreatedAt: Date, officialUrl: String
    )

    @Update
    fun modifyAccountInfo(accountInfo : AccountInfo)

    // Delete
    @Query(
        """
        UPDATE identity_info
        SET status = 1
        WHERE info_id IN (
            SELECT fk_info_id FROM account_info
            WHERE account_id = :accountId
        );
        """
    )
    fun removeAccountInfo(accountId: Long)

    @Delete
    fun removeAccountInfo(accountInfo : AccountInfo)

    @Delete
    fun removeAccountInfoList(vararg accountInfo : AccountInfo)

    // delete all
    //    @Query("DELETE FROM sqlite_sequence where name='table_task_registration'")
    //    fun clearAll()

    @Query("DELETE FROM account_info")
    fun deleteAll()
}

 */