package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment
import ru.lion_of_steel.model.Post
import ru.lion_of_steel.model.ReportComment
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
import ru.lion_of_steel.model.Note

//TODO Сначала создам для каждого класса свой отдельный сервис.
//TODO Реализую все методы необходимые в задании - касательно заметок.
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
            if (postFromPosts.id == entity.id) {
                posts[index] = entity.copy()
                return true
            }
        }
        return false
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

    override fun add(entity: Note): Note {
        notes += entity.copy(id = ++idNote)
        return notes.last()
    }

    override fun createComment(id: Int, comment: Comment): Comment {
        for (note in notes) {
            if (id == note.id) {
                comments += comment.copy()
                return comments.last()
            }
        }
        throw NotFoundException("заметка с id: $id не найдена")
    }

    override fun edit(entity: Note): Boolean {
        for ((index, noteFromNotes) in notes.withIndex()) {
            if (idNote == noteFromNotes.id) {
                notes[index] = entity.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        notes = mutableListOf()
        idNote = 0
    }
}