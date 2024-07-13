package ru.lion_of_steel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


class WallServiceTest {
    @Before
    fun clear() {
        WallService.clear()
    }

    @Test
    fun postAdd_IdPlus() {
        val post = Post(1)
        val result = WallService.postAdd(post)
        assertEquals(1,result.idPost)
    }


    @Test
    fun update_True() {
        val post = WallService.postAdd(Post(0))
        val result = WallService.update(post)
        assertTrue(result)
    }
    @Test
    fun update_False() {
        val post = Post(1)
        val result = WallService.update(post)
        assertFalse(result)
    }


}