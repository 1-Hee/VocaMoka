package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.WordCategoryDao
import com.aiden.vokamoka.data.model.WordCategory
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class WordCategoryRepository @Inject constructor(
    private val wordCategoryDao: WordCategoryDao
) : BaseRoomRepository<WordCategory>() {

    override suspend fun addEntity(entity: WordCategory): Long {
        return wordCategoryDao.insertWordCategory(entity)
    }

    override suspend fun readEntity(entityId: Long): WordCategory {
        return wordCategoryDao.selectWordCategory(entityId)
    }

    override suspend fun readEntityList(): List<WordCategory> {
        return wordCategoryDao.selectWordCategoryList()
    }

    override suspend fun modifyEntity(entity: WordCategory) {
        wordCategoryDao.updateWordCategory(entity)
    }

    override suspend fun removeEntity(entity: WordCategory) {
        wordCategoryDao.deleteWordCategory(entity)
    }

    override suspend fun removeAll() {
        wordCategoryDao.deleteAll()
    }
}