package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.WordPoolDao
import com.aiden.vokamoka.data.model.WordPool
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class WordPoolRepository @Inject constructor(
    private val wordPoolDao: WordPoolDao
) : BaseRoomRepository<WordPool>() {

    override suspend fun addEntity(entity: WordPool): Long {
        return wordPoolDao.insertWordPool(entity)
    }

    override suspend fun readEntity(entityId: Long): WordPool {
        return wordPoolDao.selectWordPool(entityId)
    }

    override suspend fun readEntityList(): List<WordPool> {
        return wordPoolDao.selectWordPoolList()
    }

    override suspend fun modifyEntity(entity: WordPool) {
        wordPoolDao.updateWordPool(entity)
    }

    override suspend fun removeEntity(entity: WordPool) {
        wordPoolDao.deleteWordPool(entity)
    }

    override suspend fun removeAll() {
        wordPoolDao.deleteAll()
    }
}