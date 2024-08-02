package ru.lion_of_steel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.lion_of_steel.exception.PostNotFoundException


class WallServiceTest {
    @Before
    fun clear() {
        WallService.clear()
    }

    @Test
    fun postAdd_IdPlus() {
        var post = Post(1, postType = "audio")
        post = post.copy(likes = updateLikes(post))
        val result = WallService.postAdd(post)
        assertEquals(1, result.idPost)
    }


    @Test
    fun update_True() {
        var post = WallService.postAdd(Post(0, postType = "audio"))
        post = post.copy(likes = updateLikes(post))
        val result = WallService.update(post)
        assertTrue(result)
    }

    @Test
    fun update_False() {
        var post = Post(1, postType = "audio")
        post = post.copy(likes = updateLikes(post))
        val result = WallService.update(post)
        assertFalse(result)
    }

    @Test
    fun createCommentReturnComment() {
        WallService.postAdd(Post(1,1))
        val comment = Comment(1)
        val result = WallService.createComment(1, comment)
        assertEquals(comment, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentExceptionNoPost() {
        val comment = Comment(2)
        WallService.createComment(1,comment)
    }
}