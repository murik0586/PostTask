package ru.lion_of_steel.service

import ru.lion_of_steel.exception.*
import ru.lion_of_steel.model.*


object WallService : CrudService<Entity> {
    private var posts = mutableListOf<Post>()
    private var notes = mutableListOf<Note>()
    private var reports = mutableListOf<ReportComment>()
    private var comments = mutableListOf<Comment>()

    private var postId = 0
    private var idNote = 0
    private var commentId = 0

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

    override fun get(entityType: Class<out Entity>): Entity {
        when (entityType) {
            Post::class.java -> {
                for (post in posts) {
                    if (!post.delete) {
                        return post
                    }
                }
                println("\n")
                throw NotFoundException("У вас нет заметок!")
            }

            Note::class.java -> {
                for (note in notes) {
                    if (!note.delete) return note
                }
                println("\n")
                throw NotFoundException("У вас нет заметок!")
            }

            else -> {
                throw NotMatchTypeException("Тип объекта не соответствует")
            }
        }
    }

    override fun getById(idEntity: Int, entityType: Class<out Entity>): Entity {
        when (entityType) {
            Post::class.java -> {
                for (post in posts) {
                    if (post.id == idEntity && !post.delete) return post

                }
                throw NotFoundException("Не нашли пост")
            }

            Note::class.java -> {
                for (note in notes) {
                    if (note.id == idEntity && !note.delete) return note

                }
                throw NotFoundException("Не нашли такую заметку")
            }

            else -> {
                throw NotMatchTypeException("Тип объекта не соответствует")
            }
        }
    }

    override fun createComment(entityType: Class<out Entity>, id: Int, comment: Comment): Comment {
        when (entityType) {
            Post::class.java -> {
                for (post in posts) {
                    if (post.id == id) {
                        comments += comment.copy(idComment = ++commentId)
                        post.comments.commentList += comment.copy(idComment = ++commentId) //идея как закинуть именно в комментарии поста
                        return comments.last()
                    }
                }
                throw NotFoundException("Пост с id: $id не найден")
            }

            Note::class.java -> {
                for (note in notes) {
                    if (id == note.id) {
                        comments += comment.copy(idComment = ++commentId) //Todo - подумать, стоит ли увеличивать автоматически id
                        note.comments.commentList += comment.copy(idComment = ++commentId)
                        return comments.last()
                    }
                }
                throw NotFoundException("заметка с id: $id не найдена")
            }

            else -> {
                throw NotMatchTypeException("Не верный тип для работы метода!")
            }
        }
    }

    //TODO начиная с удаления исправить код чтобы удалялось все у конкретного поста и конкретного note
    override fun deleteComment(entityType: Class<out Entity>, id: Int): Boolean {
        when (entityType) {
            Post::class.java -> {
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

            Note::class.java -> {
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

            else -> throw NotMatchTypeException("Тип не соответствует необходимому")

        }
    }


    override fun editComment(entityType: Class<out Entity>, idEntity: Int, commentId: Int, message: String): Boolean {
        return when (entityType) {
            Post::class.java -> {
                for (post in posts) {
                    if (post.id == idEntity) {
                        for (comment in post.comments.commentList) {
                            if (comment.idComment == commentId) {
                                if (!comment.delete) {
                                    comment.text = message
                                    return true
                                }
                                throw AccessDeniedException("Ошибка изменения: Комментарий удален")

                            }
                        }
                        throw NotFoundException("Комментарий с id $commentId - не найден")
                    }
                }
                throw NotFoundException("Пост с id $idEntity не найден")
            }

            Note::class.java -> {
                for (note in notes) {
                    if (note.id == idEntity) {
                        for (comment in note.comments.commentList) {
                            if (comment.idComment == commentId) {
                                if (!comment.delete) {
                                    comment.text = message
                                    return true
                                }
                                throw AccessDeniedException("Ошибка изменения: Комментарий удален")
                            }
                        }
                        throw NotFoundException("Комментарий с id $commentId - не найден")
                    }
                }
                throw NotFoundException("Заметка с id $idEntity не найдена")
            }

            else -> throw NotMatchTypeException("Не соответствие типа")


        }
    }

    override fun restoreComment(entityType: Class<out Entity>, idEntity: Int, commentId: Int): Boolean {
        when (entityType) {
            Post::class.java -> {
                for (post in posts) {
                    for (comment in post.comments.commentList) {
                        if (comment.idComment == commentId) {
                            if (comment.delete) {
                                !comment.delete
                                return true
                            }
                        }
                    }
                    throw NotFoundException("Не нашли комментарий с id: $commentId")
                }
                throw NotFoundException("Пост с id $idEntity не найден")

            }

            Note::class.java -> {
                for (note in notes) {
                    for (comment in note.comments.commentList) {
                        if (comment.idComment == commentId) {
                            if (comment.delete) {
                                !comment.delete
                                return true
                            }
                        }
                    }
                    throw NotFoundException("Не нашли комментарий с id: $commentId")
                }
                throw NotFoundException("Заметка с id $idEntity не найдена")

            }

            else -> throw NotMatchTypeException("Не соответствие типа")
        }
    }

    override fun getComments(entityType: Class<out Entity>, idEntity: Int): Comment {
        when (entityType) {
            Post::class.java -> {
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

            Note::class.java -> {
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
            else -> { throw NotMatchTypeException("Не соответствующий тип")}

        }
    }

        fun clear() {
            notes.clear()
            idNote = 0

            comments.clear()
            commentId = 0

            posts.clear()
            postId = 0
        }


    }




