package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.LanguageDao
import com.aiden.vokamoka.data.model.Language
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class LanguageRepository @Inject constructor(
    private val languageDao: LanguageDao
) : BaseRoomRepository<Language>() {

    override suspend fun addEntity(entity: Language): Long {
        return languageDao.insertLanguage(entity)
    }

    override suspend fun readEntity(entityId: Long): Language {
        return languageDao.selectLanguage(entityId)
    }

    override suspend fun readEntityList(): List<Language> {
        return languageDao.selectLanguageList()
    }

    override suspend fun modifyEntity(entity: Language) {
        return languageDao.updateLanguage(entity)
    }

    override suspend fun removeEntity(entity: Language) {
        return languageDao.deleteLanguage(entity)
    }

    override suspend fun removeAll() {
        languageDao.deleteAll()
    }
}