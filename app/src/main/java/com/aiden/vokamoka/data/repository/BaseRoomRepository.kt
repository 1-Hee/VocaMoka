package com.aiden.vokamoka.data.repository

abstract class BaseRoomRepository<T> {

    // Create
    abstract suspend fun addEntity(entity:T): Long
    // Read
    abstract suspend fun readEntity(entityId: Long): T
    abstract suspend fun readEntityList(): List<T>

    // Update
    abstract suspend fun modifyEntity(entity: T)
    // Delete
    // abstract suspend fun removeEntity(entityId: Long)
    abstract suspend fun removeEntity(entity: T)
    abstract suspend fun removeAll()

}