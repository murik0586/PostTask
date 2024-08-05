package ru.lion_of_steel.service

import ru.lion_of_steel.model.Comment
import ru.lion_of_steel.model.Post
import ru.lion_of_steel.model.ReportComment
import ru.lion_of_steel.exception.PostNotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
//TODO Сначала создам для каждого класса свой отдельный сервис.
//TODO Реализую все методы необходимые в задании - касательно заметок.
//TODO Затем попробую использовать дженерики, сделать методы общими.
//TODO А для этого я создам для них отдельный интерфейс.
//Предполагаю, что здесь активно понадобятся операторы is и as - при необходимости прочитать.

object WallPostService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<ReportComment>()
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
                val reportComment = ReportComment(fromId,idComment,reason,msg)
                reports += reportComment.copy()
                return reportComment
            }
        }
        throw ReportNoOwnerAndCommentIdException("Несуществующий комментарий, возможно комментарий уже удален")
    }

    fun createComment(idPost: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.idPost == idPost) {
                comments += comment.copy()
                return comment
            }
        }
        throw PostNotFoundException("Пост с id: $idPost не найден")
    }

    fun postAdd(post: Post): Post {
        posts += post.copy(idPost = ++postId)
        return posts.last()
    }


    fun update(post: Post): Boolean {
        for ((index, postFromPosts) in posts.withIndex()) {
            if (postFromPosts.idPost == post.idPost) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        postId = 0
    }
}

object WallNoteService  {

}