package ru.lion_of_steel.service

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.model.User

val service = ServiceUser

class ServiceUserTest {
    @Before
    fun clear() {
        ChatService.clear()
        ServiceUser.clear()
    }

    @Test
    fun addUser() {
        val user = User(1, "Мурат")
        val result = service.addUser(user)
        assertEquals(user, result)
    }

    @Test
    fun getUsersSuccess() {
        val user1 = User(1, "Мурат")
        val user2 = User(2, "Тимур")
        service.addUser(user1)
        service.addUser(user2)
        val exception = mutableListOf(user1,user2)
        val result = ServiceUser.getUsers()
        assertEquals(exception,result)
    }
    @Test(expected = NotFoundException::class)
    fun getUsersException() {

       ServiceUser.getUsers()

    }

    @Test
    fun getUserSuccess() {
        val user1 = User(1, "Мурат")
        val user2 = User(2, "Тимур")
        service.addUser(user1)
        service.addUser(user2)
        val result = ServiceUser.getUser(user1,user2)
        assertEquals(true,result)
    }
    @Test(expected = NotFoundException::class)
    fun getUserException() {
        val user1 = User(1, "Мурат")
        val user2 = User(2, "Тимур")
        service.addUser(user1)
        ServiceUser.getUser(user1,user2)

    }


}