package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment



interface CrudService<T> {
    fun add(entity: T): T
    fun edit(entity: T): Boolean
    fun delete(idEntity:Int, entityType: Class<out T>): Boolean
    fun get(entityType: Class<out T>): T//используется тип для получения всего списка ОПРЕДЕЛЕННОГО ОБЪЕКТА
    fun getById(idEntity: Int, entityType: Class<out T>): T

    fun createComment(entityType: Class<out T>, id: Int, comment: Comment): Comment
    fun deleteComment(entityType: Class<out T>,id: Int): Boolean
    fun editComment(entityType: Class<out T>,idEntity: Int,commentId: Int,message: String):Boolean
    fun restoreComment(entityType: Class<out T>,idEntity: Int,commentId: Int): Boolean
    fun getComments(entityType: Class<out T>,idEntity: Int): Comment

}
