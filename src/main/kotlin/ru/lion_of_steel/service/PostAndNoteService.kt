package ru.lion_of_steel.service

import ru.lion_of_steel.exception.AccessDeniedException
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
import ru.lion_of_steel.model.*


//TODO Затем попробую использовать дженерики, сделать методы общими.
//Предполагаю, что здесь активно понадобятся операторы is и as - при необходимости прочитать.

object WallPostService : CrudService<Post> {
    private var posts = mutableListOf<Post>()
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<ReportComment>()
    private var postId = 0

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

    override fun createComment(id: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == id) {
                comments += comment.copy()
                post.comments.commentList ?: comment.copy() //идея как закинуть именно в комментарии поста
                return comments.last()
            }
        }
        throw NotFoundException("Пост с id: $id не найден")
    }

    override fun add(entity: Post): Post {
        posts += entity.copy(id = ++postId)
        return posts.last()
    }


    override fun edit(entity: Post): Boolean {
        for ((index, postFromPosts) in posts.withIndex()) {
            if (entity.id == postFromPosts.id) {
                if (!postFromPosts.delete) {//если не удален
                    posts[index] = entity.copy()
                    return true
                } else {
                    throw NotFoundException("Пост с id ${entity.id} - не найден(Возможно удален)")
                }
            }
        }
        return false
    }

    override fun deleteComment(id: Int): Boolean {
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

    override fun get(): Post {
        for (post in posts) {
            if (post.delete) continue else return post
        }
        println("\n")
        throw NotFoundException("У вас нет заметок!")
    }

    override fun getById(idEntity: Int): Post {
        for (post in posts) {
            if (post.id == idEntity && !post.delete) return post

        }
        throw NotFoundException("Не нашли пост")
    }

    override fun editComment(commentId: Int, message: String): Boolean {
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


    override fun delete(idEntity: Int): Boolean {
        for (post in posts) {
            if (post.id == idEntity) {
                post.delete = true
                return true
            }
        }
        throw NotFoundException("Пост с id $idEntity - не найден")

    }

    override fun restoreComment(commentId: Int): Boolean {
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

    override fun getComments(idEntity: Int): Comment {
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
        posts = mutableListOf()
        comments = mutableListOf()
        postId = 0
    }
}

object WallNoteService : CrudService<Note> {
    private var notes = mutableListOf<Note>()
    private var idNote = 0
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<ReportComment>()
    override fun add(entity: Note): Note {
        notes += entity.copy(id = ++idNote)
        return notes.last()
    }

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

    override fun createComment(id: Int, comment: Comment): Comment {
        for (note in notes) {
            if (id == note.id) {
                comments += comment.copy() //Todo - подумать, стоит ли увеличивать автоматически id
                note.comments.commentList ?: comment.copy()
                return comments.last()
            }
        }
        throw NotFoundException("заметка с id: $id не найдена")
    }

    override fun deleteComment(id: Int): Boolean {
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

    override fun editComment(commentId: Int, message: String): Boolean {
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

    override fun restoreComment(commentId: Int): Boolean {
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

    override fun getComments(idEntity: Int): Comment {
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

    override fun edit(entity: Note): Boolean {
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
        return false
    }

    override fun delete(idEntity: Int): Boolean {
        for (note in notes) {
            if (note.id == idEntity) {
                note.delete = true
                return true
            }
        }
        throw NotFoundException("Заметка с id $idEntity - не найдена")

    }

    override fun get(): Note {
        for (note in notes) {
            if (note.delete) continue
            return note
        }
        println("\n")
        throw NotFoundException("У вас нет заметок!")
    }

    override fun getById(idEntity: Int): Note {
        for (note in notes) {
            if (note.id == idEntity && !note.delete) return note

        }
        throw NotFoundException("Не нашли такую заметку")
    }

    fun clear() {
        notes = mutableListOf()
        idNote = 0
    }

}