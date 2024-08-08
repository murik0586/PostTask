package ru.lion_of_steel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException
import ru.lion_of_steel.model.Comment
import ru.lion_of_steel.model.Post
import ru.lion_of_steel.model.ReportComment
import ru.lion_of_steel.model.updateLikes
import ru.lion_of_steel.service.WallService


//class WallPostServiceTest {
//    @Before
//    fun clear() {
//        WallService.clear()
//    }
//
//    @Test
//    fun postAdd_IdPlus() {
//        var post = Post(1, postType = "audio")
//        post = post.copy(likes = updateLikes(post))
//        val result = WallService.add(post)
//        assertEquals(1, result.id)
//    }
//
//
//    @Test
//    fun update_True() {
//        var post = WallService.add(Post(0, postType = "audio"))
//        post as Post
//        post = post.copy(likes = updateLikes(post))
//        val result = WallService.edit(post)
//        assertTrue(result)
//    }
//
//    @Test
//    fun update_False() {
//        var post = Post(1, postType = "audio")
//        post = post.copy(likes = updateLikes(post))
//        val result = WallService.edit(post)
//        assertFalse(result)
//    }
//
//    @Test
//    fun createCommentReturnComment() {
//        WallService.add(Post(1, 1))
//        val comment = Comment(1)
//        val result = WallPostService.createComment(1, comment)
//        assertEquals(comment, result)
//    }
//
//    @Test(expected = NotFoundException::class)
//    fun createCommentExceptionNoPost() {
//        val comment = Comment(2)
//        WallPostService.createComment(1, comment)
//    }
//
//    @Test(expected = ReportNoOwnerAndCommentIdException::class)
//    fun addReportCommentNoComment() {
//        WallPostService.addReportComment(1, 1, 0)
//
//    }
//
//    @Test(expected = ReasonNotReportException::class)
//    fun addReportCommentNoFoundReason() {
//        WallService.add(Post(1, 2))
//        WallPostService.createComment(1, Comment(1, 1))
//        WallPostService.addReportComment(1, 1, 8)
//
//    }
//
//    @Test
//    fun addReportSuccess() {
//        WallService.add(Post(1, 2))
//        WallPostService.createComment(1, Comment(1, 1))
//        val report = ReportComment(1, 1, 0, "Спам")
//        val result = WallPostService.addReportComment(1, 1, 0)
//        assertEquals(report, result)
//    }
//}