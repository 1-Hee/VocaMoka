package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.ConceptDao
import com.aiden.vokamoka.data.model.Concept
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ConceptRepository @Inject constructor(
    private val conceptDao: ConceptDao
) : BaseRoomRepository<Concept>() {

    override suspend fun addEntity(entity: Concept): Long {
        return conceptDao.insertConcept(entity)
    }

    override suspend fun readEntity(entityId: Long): Concept {
        return conceptDao.selectConcept(entityId)
    }

    override suspend fun readEntityList(): List<Concept> {
        return conceptDao.selectConceptList()
    }

    override suspend fun modifyEntity(entity: Concept) {
        conceptDao.updateConcept(entity)
    }

    override suspend fun removeEntity(entity: Concept) {
        conceptDao.deleteConcept(entity)
    }

    override suspend fun removeAll() {
        conceptDao.deleteAll()
    }
}