package com.aiden.vokamoka.data.repository

import com.aiden.vokamoka.data.dao.ExpressionDao
import com.aiden.vokamoka.data.model.Expression
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ExpressionRepository @Inject constructor(
    private val expressionDao: ExpressionDao
) : BaseRoomRepository<Expression>() {

    override suspend fun addEntity(entity: Expression): Long {
        return expressionDao.insertExpression(entity)
    }

    override suspend fun readEntity(entityId: Long): Expression {
        return expressionDao.selectExpression(entityId)
    }

    override suspend fun readEntityList(): List<Expression> {
        return expressionDao.selectExpressionList()
    }

    override suspend fun modifyEntity(entity: Expression) {
        expressionDao.updateExpression(entity)
    }

    override suspend fun removeEntity(entity: Expression) {
        expressionDao.deleteExpression(entity)
    }

    override suspend fun removeAll() {
        expressionDao.deleteAll()
    }
}