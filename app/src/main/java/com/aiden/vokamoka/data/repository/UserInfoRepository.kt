package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.UserInfoDao
import com.aiden.vokamoka.data.model.UserInfo
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserInfoRepository @Inject constructor(
    private val userInfoDao: UserInfoDao
) : BaseRoomRepository<UserInfo>() {

    override suspend fun addEntity(entity: UserInfo): Long {
        return userInfoDao.insertUserInfo(entity)
    }

    override suspend fun readEntity(entityId: Long): UserInfo {
        return userInfoDao.selectUserInfo(entityId)
    }

    suspend fun readLastEntityId(): Long {
        return userInfoDao.selectLastUserId()
    }


    override suspend fun readEntityList(): List<UserInfo> {
        return userInfoDao.selectUserInfoList()
    }

    override suspend fun modifyEntity(entity: UserInfo) {
        userInfoDao.updateUserInfo(entity)
    }

    override suspend fun removeEntity(entity: UserInfo) {
        userInfoDao.deleteUserInfo(entity)
    }

    override suspend fun removeAll() {
        userInfoDao.deleteAll()
    }


}