package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment


interface CrudService<T> {
    fun add(entity: T): T
    fun edit(entity: T): Boolean
    fun delete(idEntity:Int, entityType: Class<out T>): Boolean
    fun get(): T
    fun getById(idEntity: Int): T

    fun createComment(id: Int, comment: Comment): Comment
    fun deleteComment(id: Int): Boolean
    fun editComment(commentId: Int,message: String):Boolean
    fun restoreComment(commentId: Int): Boolean
    fun getComments(idEntity: Int): Comment

}
