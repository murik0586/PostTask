package ru.lion_of_steel.service

import ru.lion_of_steel.exception.*
import ru.lion_of_steel.model.*

//TODO Затем попробую использовать дженерики, сделать методы общими.
//Предполагаю, что здесь активно понадобятся операторы is и as - при необходимости прочитать.
object WallService : CrudService<Entity> {
    private var posts = mutableListOf<Post>()
    private var notes = mutableListOf<Note>()
    private var reports = mutableListOf<ReportComment>()

    private var postId = 0
    private var idNote = 0

    override fun add(entity: Entity): Entity {
        return when (entity) {
            is Post -> {
                posts += entity.copy(id = ++postId)
                posts.last()
            }

            is Note -> {
                notes += entity.copy(id = ++idNote)
                notes.last()

            }

            else -> throw NotMatchTypeException("Тип объекта не соответствует")
        }
    }


    override fun edit(entity: Entity): Boolean {
        return when (entity) {
            is Post -> {
                for ((index, postFromPosts) in posts.withIndex()) {
                    if (entity.id == postFromPosts.id) {
                        if (!postFromPosts.delete) {
                            posts[index] = entity.copy()
                            return true
                        } else {
                            throw NotFoundException("Пост с id ${entity.id} - не найден(Возможно удален)")
                        }
                    }
                }
                false
            }

            is Note -> {
                for ((index, noteFromNotes) in notes.withIndex()) {
                    if (entity.id == noteFromNotes.id) {
                        if (!noteFromNotes.delete) {//если не удален
                            notes[index] = entity.copy()
                            return true
                        } else {
                            throw NotFoundException("Заметка с id ${entity.id} - не найдена(Возможно удалена)")
                        }
                    }
                }
                false
            }

            else -> throw NotMatchTypeException("Тип объекта не соответствует")

        }

    }

    override fun delete(idEntity: Int, entityType: Class<out Entity>): Boolean {
        when (entityType) {

            Post::class.java -> {
                for (post in posts) {
                    if (post.id == idEntity) {
                        post.delete = true
                        return true
                    }
                }
                throw NotFoundException("Пост с id $idEntity - не найден")

            }

            Note::class.java -> {

                    for (note in notes) {
                        if (note.id == idEntity) {
                            note.delete = true
                            return true
                        }
                    }
                throw NotFoundException("Заметка с id $idEntity - не найдена")

            }

            else -> throw NotMatchTypeException("Тип объекта не соответствует")
        }
    }

    override fun get(): Entity {
        TODO("Not yet implemented")
    }

    override fun getById(idEntity: Int): Entity {
        TODO("Not yet implemented")
    }

    override fun createComment(id: Int, comment: Comment): Comment {
        TODO("Not yet implemented")
    }

    override fun deleteComment(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun editComment(commentId: Int, message: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun restoreComment(commentId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getComments(idEntity: Int): Comment {
        TODO("Not yet implemented")
    }

    fun clear() {
        notes.clear()
        idNote = 0

        posts.clear()
        postId = 0
    }
}

object WallPostService {
    private var posts = mutableListOf<Post>()
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<ReportComment>()

    fun addReportComment(idComment: Int, fromId: Int, reason: Int): ReportComment {
        for (comment in comments) {
            if (comment.idComment == idComment && comment.fromId == fromId) {
                val msg = when (reason) {
                    0 -> "Спам"
                    1 -> "Детская порнография"
                    2 -> "Экстремизм"
                    3 -> "Насилие"
                    4 -> "Пропаганда наркотиков"
                    5 -> "Материал для взрослых"
                    6 -> "Оскорбление"
                    7 -> "Призывы к суициду."
                    else -> throw ReasonNotReportException("Такой причины не существует")

                }
                val reportComment = ReportComment(fromId, idComment, reason, msg)
                reports += reportComment.copy()
                return reportComment
            }
        }
        throw ReportNoOwnerAndCommentIdException("Несуществующий комментарий, возможно комментарий уже удален")
    }

    fun createComment(id: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == id) {
                comments += comment.copy()
                post.comments.commentList ?: comment.copy() //идея как закинуть именно в комментарии поста
                return comments.last()
            }
        }
        throw NotFoundException("Пост с id: $id не найден")
    }


    fun deleteComment(id: Int): Boolean {
        for (comment in comments) {
            if (id == comment.idComment) {
                if (!comment.delete) {
                    comment.delete//делаем true
                    return true
                } else {
                    throw AccessDeniedException("Ошибка доступа: Комментарий уже удален")
                }
            }
        }
        throw NotFoundException("Комментарий с id $id - не найден")
    }

    fun get(): Post {
        for (post in posts) {
            if (post.delete) continue else return post
        }
        println("\n")
        throw NotFoundException("У вас нет заметок!")
    }

    fun getById(idEntity: Int): Post {
        for (post in posts) {
            if (post.id == idEntity && !post.delete) return post

        }
        throw NotFoundException("Не нашли пост")
    }

    fun editComment(commentId: Int, message: String): Boolean {
        for (comment in comments) {
            if (comment.idComment == commentId) {
                if (!comment.delete) {
                    comment.text = message
                    return true
                }
                throw AccessDeniedException("Ошибка изменения: Комментарий уже удален")
            }
        }
        throw NotFoundException("Комментарий с id $commentId - не найден")
    }


    fun restoreComment(commentId: Int): Boolean {
        for (comment in comments) {
            if (comment.idComment == commentId) {
                if (comment.delete) {
                    !comment.delete
                    return true
                }
            }
        }
        throw NotFoundException("Не нашли комментарий с id: $commentId")
    }

    fun getComments(idEntity: Int): Comment {
        for (post in posts) {
            if (idEntity == post.id) {
                for (comment in post.comments.commentList) {
                    if (!comment.delete) {
                        println("\n")
                        return comment
                    }
                }
                throw NotFoundException("Список комментариев пуст")

            }
        }
        throw NotFoundException("Такой пост отсутствует..")
    }


    fun clear() {

    }
}

object WallNoteService {
    private var notes = mutableListOf<Note>()
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<ReportComment>()


    fun addReportComment(idComment: Int, fromId: Int, reason: Int): ReportComment {
        for (comment in comments) {
            if (comment.idComment == idComment && comment.fromId == fromId) {
                val msg = when (reason) {
                    0 -> "Спам"
                    1 -> "Детская порнография"
                    2 -> "Экстремизм"
                    3 -> "Насилие"
                    4 -> "Пропаганда наркотиков"
                    5 -> "Материал для взрослых"
                    6 -> "Оскорбление"
                    7 -> "Призывы к суициду."
                    else -> throw ReasonNotReportException("Такой причины не существует")

                }
                val reportComment = ReportComment(fromId, idComment, reason, msg)
                reports += reportComment.copy()
                return reportComment
            }
        }
        throw ReportNoOwnerAndCommentIdException("Несуществующий комментарий, возможно комментарий уже удален")
    }

    fun createComment(id: Int, comment: Comment): Comment {
        for (note in notes) {
            if (id == note.id) {
                comments += comment.copy() //Todo - подумать, стоит ли увеличивать автоматически id
                note.comments.commentList ?: comment.copy()
                return comments.last()
            }
        }
        throw NotFoundException("заметка с id: $id не найдена")
    }

    fun deleteComment(id: Int): Boolean {
        for (comment in comments) {
            if (id == comment.idComment) {
                if (!comment.delete) {
                    comment.delete//делаем true
                    return true
                } else {
                    throw AccessDeniedException("Ошибка доступа: Комментарий уже удален")
                }
            }
        }
        throw NotFoundException("Комментарий с id $id - не найден")
    }

    fun editComment(commentId: Int, message: String): Boolean {
        for (comment in comments) {
            if (comment.idComment == commentId) {
                if (!comment.delete) {
                    comment.text = message
                    return true
                }
                throw AccessDeniedException("Ошибка изменения: Комментарий уже удален")
            }
        }
        throw NotFoundException("Комментарий с id $commentId - не найден")
    }

    fun restoreComment(commentId: Int): Boolean {
        for (comment in comments) {
            if (comment.idComment == commentId) {
                if (comment.delete) {
                    !comment.delete
                    return true
                }
            }
        }
        throw NotFoundException("Не нашли комментарий с id: $commentId")
    }

    fun getComments(idEntity: Int): Comment {
        for (note in notes) {
            if (idEntity == note.id) {
                for (comment in note.comments.commentList) {
                    if (!comment.delete) {
                        println("\n")
                        return comment
                    }
                }
                throw NotFoundException("Список комментариев пуст")

            }
        }
        throw NotFoundException("Такая заметка отсутствует..")
    }




    fun get(): Note {
        for (note in notes) {
            if (note.delete) continue
            return note
        }
        println("\n")
        throw NotFoundException("У вас нет заметок!")
    }

    fun getById(idEntity: Int): Note {
        for (note in notes) {
            if (note.id == idEntity && !note.delete) return note

        }
        throw NotFoundException("Не нашли такую заметку")
    }


}