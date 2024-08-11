package ru.lion_of_steel.service

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.lion_of_steel.exception.AccessDeniedException
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
import ru.lion_of_steel.model.*

class WallPostServiceTest {
    @Before
    fun clear() {
        WallService.clear()
    }

    @Test
    fun postAdd_IdPlus() {
        val result = WallService.add(Post(1))
        assertEquals(1, result?.id)
    }


    @Test
    fun edit_True() {
        var post = WallService.add(Post(0, postType = "audio"))
        post as Post
        post = post.copy(postType = "video")
        val result = WallService.edit(post)
        assertTrue(result)
    }

    @Test
    fun edit_False() {
        var post = Post(1, postType = "audio")
        post = post.copy(likes = updateLikes(post))
        val result = WallService.edit(post)
        assertFalse(result)
    }

    @Test(expected = NotFoundException::class)
    fun editPostNoFoundId() {
        WallService.add(Post(1, delete = true))
        WallService.edit(Post(1, contentText = "ldsfasf"))

        //проверка на Exception
    }

    @Test
    fun deleteTrue() {
        WallService.add(Post(1))
        val result = WallService.delete(1, Post::class.java)
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteNoteNotFoundException() {
        WallService.delete(1, Post::class.java)

    }

    @Test
    fun getPostSuccess() {
        WallService.add(Post(1))
        WallService.add(Post(2, delete = true))
        WallService.add(Post(3))
        val expectation = mutableListOf(Post(1), Post(3))
        val result = WallService.get(Post::class.java)
        assertEquals(expectation, result)
        //тест на отображение списка с игнором удаленного поста
    }

    @Test(expected = NotFoundException::class)
    fun getNoteFalse() {

        WallService.get(Post::class.java)

        //тест на отображение списка с игнором удаленной заметки
    }

    @Test
    fun createCommentSuccess() {
        WallService.add(Post(1))
        val expectation = Comment(1)
        val result = WallService.createComment(Post::class.java, 1, Comment(1))
        assertEquals(expectation, result)
    }

    @Test(expected = NotFoundException::class)
    fun createCommentException() {
        WallService.createComment(Post::class.java, 1, Comment(1))

    }//на не найдена по id сущность(пост)

    @Test
    fun deleteCommentTrue() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1))
        val result = WallService.deleteComment(Post::class.java, 1, 1)
        assertTrue(result)
    }

    @Test(expected = AccessDeniedException::class)
    fun deleteCommentFalse() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1, delete = true))
        WallService.deleteComment(Post::class.java, 1, 1)

    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentFalseNoComment() {
        WallService.add(Post(1))
        WallService.deleteComment(Post::class.java, 1, 1)

    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentFalseNoPost() {

        WallService.deleteComment(Post::class.java, 1, 1)

    }

    @Test(expected = ReportNoOwnerAndCommentIdException::class)
    fun addReportCommentNoComment() {
        val post = WallService.add(Post(1))
        WallService.addReportComment(Post::class.java, post?.id ?: 0, 1, 1, 0)

    }

    @Test(expected = ReasonNotReportException::class)
    fun addReportCommentNoFoundReason() {
        WallService.add(Post(1, 2))
        WallService.createComment(Post::class.java, 1, Comment(1, 1))
        WallService.addReportComment(Post::class.java, 1, 1, 1, 8)

    }

    @Test
    fun addReportSuccess() {
        WallService.add(Post(1, 2))
        WallService.createComment(Post::class.java, 1, Comment(1, 1))
        val report = ReportComment(1, 1, 0, "Спам")
        val result = WallService.addReportComment(Post::class.java, 1, 1, 1, 0)
        assertEquals(report, result)
    }


    @Test
    fun getByIdTrue() {
        WallService.add(Post(1))
        val result = WallService.getById(1, Post::class.java)
        assertEquals(Post(1), result)
    }

    @Test(expected = NotFoundException::class)
    fun getByIdException() {
        WallService.getById(1, Note::class.java)

    }

    @Test
    fun editCommentTrue() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1))
        val result = WallService.editComment(Post::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }

    @Test(expected = AccessDeniedException::class)
    fun editCommentDeniedException() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1, delete = true))
        val result = WallService.editComment(Post::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }//попытка изменить удаленный комментарий

    @Test(expected = NotFoundException::class)
    fun editCommentNotFoundExceptionComment() {
        WallService.add(Post(1))
        val result = WallService.editComment(Post::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }//На отсутствие комментария

    @Test(expected = NotFoundException::class)
    fun editCommentNotFoundExceptionPost() {
        val result = WallService.editComment(Post::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }//На отсутствие поста

    @Test
    fun restoreCommentTrue() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1, delete = true))
        val result = WallService.restoreComment(Post::class.java, 1, 1)
        assertTrue(result)
    }//на успешное завершение

    @Test(expected = NotFoundException::class)
    fun restoreCommentNotFoundDeleteComment() {
        WallService.add(Post(1))
        WallService.createComment(Post::class.java, 1, Comment(1))
        WallService.restoreComment(Post::class.java, 1, 1)
    }//на отсутствие комментария среди удаленных

    @Test(expected = NotFoundException::class)
    fun restoreCommentNotFoundPost() {
        WallService.createComment(Post::class.java, 1, Comment(1, delete = true))
        WallService.restoreComment(Post::class.java, 1, 1)
    }//на отсутствие поста

    @Test
    fun getCommentsSuccess() {
        WallService.add(Post(1))
        val expectation = listOf(Comment(1), Comment(2), Comment(3), Comment(4))
        WallService.createComment(Post::class.java, 1, Comment(1))
        WallService.createComment(Post::class.java, 1, Comment(2))
        WallService.createComment(Post::class.java, 1, Comment(3))
        WallService.createComment(Post::class.java, 1, Comment(4))
        val result = WallService.getComments(Post::class.java, 1)
        assertEquals(expectation, result)
    }//на вывод списка комментариев!

    @Test(expected = NotFoundException::class)
    fun getCommentsEmptyCommentsException() {
        WallService.add(Post(1))
        WallService.getComments(Post::class.java, 1)

    }//Исключение - список комментов пуст!

    @Test(expected = NotFoundException::class)
    fun getCommentsNotFoundExceptionNote() {
        WallService.createComment(Post::class.java, 1, Comment(1))
          WallService.getComments(Post::class.java, 1)
    }//исключение - отсутствие поста!
}