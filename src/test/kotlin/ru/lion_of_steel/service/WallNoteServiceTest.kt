package ru.lion_of_steel.service

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.lion_of_steel.exception.AccessDeniedException
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
import ru.lion_of_steel.model.Comment
import ru.lion_of_steel.model.Note
import ru.lion_of_steel.model.ReportComment

class WallNoteServiceTest {
    @Before
    fun clear() {
        WallService.clear()
    } //очистка

    @Test
    fun noteAdd_IdPlus() {
        val result = WallService.add(Note(0))
        assertEquals(1, result?.id)
    }//проверка на увеличение id

    @Test
    fun edit_True() {
        var note = WallService.add(Note(0))
        note as Note
        note = note.copy(text = "...")
        val result = WallService.edit(note)
        assertTrue(result)
    }//Проверка на успешное изменение

    @Test
    fun edit_False() {
        var note = Note(0, text = "audio")
        note = note.copy()
        val result = WallService.edit(note)
        assertFalse(result)
        //проверка на неуспешное изменение
    }

    @Test(expected = NotFoundException::class)
    fun editNoteNoFoundId() {
        WallService.add(Note(1, delete = true))
        WallService.edit(Note(1, text = "hello"))

        //проверка на Exception
    }

    @Test
    fun deleteTrue() {
        WallService.add(Note(1))
        val result = WallService.delete(1, Note::class.java)
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteNoteNotFoundException() {
        WallService.delete(1, Note::class.java)

    }

    @Test
    fun getNoteSuccess() {
        WallService.add(Note(1))
        WallService.add(Note(2, delete = true))
        WallService.add(Note(3))
        val expectation = mutableListOf(Note(1), Note(3))
        val result = WallService.get(Note::class.java)
        assertEquals(expectation, result)
        //тест на отображение списка с игнором удаленной заметки
    }

    @Test(expected = NotFoundException::class)
    fun getNoteFalseException() {

        WallService.get(Note::class.java)

        //тест на отображение списка с игнором удаленной заметки
    }

    @Test
    fun createCommentSuccess() {
        WallService.add(Note(1))
        val expectation = Comment(1)
        val result = WallService.createComment(Note::class.java, 1, Comment(1))
        assertEquals(expectation, result)
    }

    @Test(expected = NotFoundException::class)
    fun createCommentException() {
        WallService.createComment(Note::class.java, 1, Comment(1))

    }//на не найдена по id сущность(заметка)

    @Test
    fun deleteCommentTrue() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1))
        val result = WallService.deleteComment(Note::class.java, 1, 1)
        assertTrue(result)
    }

    @Test(expected = AccessDeniedException::class)
    fun deleteCommentFalse() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1, delete = true))
        WallService.deleteComment(Note::class.java, 1, 1)

    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentFalseNoComment() {
        WallService.add(Note(1))
        WallService.deleteComment(Note::class.java, 1, 1)

    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentFalseNoNote() {

        WallService.deleteComment(Note::class.java, 1, 1)

    }


    @Test(expected = ReasonNotReportException::class)
    fun addReportCommentNoFoundReason() {
        WallService.add(Note(1, 2))
        WallService.createComment(Note::class.java, 1, Comment(1, 1))
        WallService.addReportComment(Note::class.java, 1, 1, 1, 8)

    }

    @Test
    fun addReportSuccess() {
        WallService.add(Note(1, 2))
        WallService.createComment(Note::class.java, 1, Comment(1, 1))
        val report = ReportComment(1, 1, 0, "Спам")
        val result = WallService.addReportComment(Note::class.java, 1, 1, 1, 0)
        assertEquals(report, result)
    }

    @Test(expected = ReportNoOwnerAndCommentIdException::class)
    fun addReportCommentNoComment() {
        val note = WallService.add(Note(1))
        WallService.addReportComment(Note::class.java, note?.id ?: 0, 1, 1, 0)
    }

    @Test
    fun getByIdTrue() {
        WallService.add(Note(1))
        val result = WallService.getById(1, Note::class.java)
        assertEquals(Note(1), result)
    }

    @Test(expected = NotFoundException::class)
    fun getByIdException() {
        WallService.getById(1, Note::class.java)

    }

    @Test
    fun editCommentTrue() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1))
        val result = WallService.editComment(Note::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }//еще на исключения

    @Test(expected = AccessDeniedException::class)
    fun editCommentDeniedException() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1, delete = true))
        val result = WallService.editComment(Note::class.java, 1, 1, " Привет!")
        assertTrue(result)
    }//попытка изменить удаленный комментарий

    @Test(expected = NotFoundException::class)
    fun editCommentNotFoundExceptionComment() {
        WallService.add(Note(1))
        //WallService.createComment(Note::class.java,1,Comment(1))
        WallService.editComment(Note::class.java, 1, 1, " Привет!")

    }//На отсутствие комментария

    @Test(expected = NotFoundException::class)
    fun editCommentNotFoundExceptionNote() {
        //WallService.add(Note(1))
        //WallService.createComment(Note::class.java,1,Comment(1))
        WallService.editComment(Note::class.java, 1, 1, " Привет!")
    }//На отсутствие комментария

    @Test
    fun restoreCommentTrue() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1, delete = true))
        val result = WallService.restoreComment(Note::class.java, 1, 1)
        assertTrue(result)
    }//на успешное завершение

    @Test(expected = NotFoundException::class)
    fun restoreCommentNotFoundDeleteComment() {
        WallService.add(Note(1))
        WallService.createComment(Note::class.java, 1, Comment(1))
        WallService.restoreComment(Note::class.java, 1, 1)
    }//на отсутствие комментария среди удаленных

    @Test(expected = NotFoundException::class)
    fun restoreCommentNotFoundNote() {
        WallService.createComment(Note::class.java, 1, Comment(1, delete = true))
        WallService.restoreComment(Note::class.java, 1, 1)
    }//на отсутствие заметки

    @Test
    fun getCommentsSuccess() {
        WallService.add(Note(1))
        val expectation = listOf(Comment(1), Comment(2), Comment(3), Comment(4))
        WallService.createComment(Note::class.java, 1, Comment(1))
        WallService.createComment(Note::class.java, 1, Comment(2))
        WallService.createComment(Note::class.java, 1, Comment(3))
        WallService.createComment(Note::class.java, 1, Comment(4))
        val result = WallService.getComments(Note::class.java, 1)
        assertEquals(expectation, result)
    }//на вывод списка комментариев!

    @Test(expected = NotFoundException::class)
    fun getCommentsEmptyCommentsException() {
        WallService.add(Note(1))
        WallService.getComments(Note::class.java, 1)

    }//Исключение - список комментов пуст!

    @Test(expected = NotFoundException::class)
    fun getCommentsNotFoundExceptionNote() {
        WallService.createComment(Note::class.java, 1, Comment(1))
        WallService.getComments(Note::class.java, 1)
    }//исключение - отсутствие заметки!


}