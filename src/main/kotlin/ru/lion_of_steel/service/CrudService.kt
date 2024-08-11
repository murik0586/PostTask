package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment
import ru.lion_of_steel.model.Entity


interface CrudService<T> {
    fun add(entity: T): Entity?
    fun edit(entity: T): Boolean
    fun delete(idEntity:Int, entityType: Class<out T>): Boolean
    fun get(entityType: Class<out T>): List<T>//используется тип для получения всего списка ОПРЕДЕЛЕННОГО ОБЪЕКТА
    fun getById(idEntity: Int, entityType: Class<out T>): T

    fun createComment(entityType: Class<out T>, id: Int, comment: Comment): Comment
    fun deleteComment(entityType: Class<out T>,idEntity: Int,id: Int): Boolean
    fun editComment(entityType: Class<out T>,idEntity: Int,commentId: Int,message: String):Boolean
    fun restoreComment(entityType: Class<out T>,idEntity: Int,commentId: Int): Boolean
    fun getComments(entityType: Class<out T>,idEntity: Int): List<Comment>

}
