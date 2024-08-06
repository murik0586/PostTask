package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment

interface CrudService<T> {
    fun add(entity: T): T
    fun edit(entity: T): Boolean
    fun createComment(id: Int, comment: Comment): Comment


}
