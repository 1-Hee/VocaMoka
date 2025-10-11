package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.StudyScheduleDao
import com.aiden.vokamoka.data.model.StudySchedule
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class StudyScheduleRepository @Inject constructor(
    private val studyScheduleDao: StudyScheduleDao
) : BaseRoomRepository<StudySchedule>() {

    override suspend fun addEntity(entity: StudySchedule): Long {
        return studyScheduleDao.insertStudySchedule(entity)
    }

    override suspend fun readEntity(entityId: Long): StudySchedule {
        return studyScheduleDao.selectStudySchedule(entityId)
    }

    override suspend fun readEntityList(): List<StudySchedule> {
        return studyScheduleDao.selectStudyScheduleList()
    }

    override suspend fun modifyEntity(entity: StudySchedule) {
        studyScheduleDao.updateStudySchedule(entity)
    }

    override suspend fun removeEntity(entity: StudySchedule) {
        studyScheduleDao.deleteStudySchedule(entity)
    }

    override suspend fun removeAll() {
        studyScheduleDao.deleteAll()
    }
}