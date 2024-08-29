package ru.lion_of_steel.model

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.service.ChatService
import ru.lion_of_steel.service.ServiceUser

val serviceUser = ServiceUser
val serviceChat = ChatService

class UserTest {
    @Before
    fun clear() {
        serviceChat.clear()
        serviceUser.clear()
    }

    @Test
    fun pushMessageSuccess() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        val result = user.pushMessage(userTwo, message)
        assertEquals(message,result)
    }

    @Test(expected = NotFoundException::class)
    fun editMessageExceptionNoChatNoMessage() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        user.pushMessage(userTwo, message)
        val exceptionMessage = Message(2,"Как дела?!", userTwo)
        val result = user.editMessage(userTwo,3,"Как дела?!")
        assertEquals(exceptionMessage,result)
    }
    @Test(expected = NotFoundException::class)
    fun editMessageExceptionNoChat() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        Message(1, "Привет!", userTwo)
        //user.pushMessage(userTwo, message)
        Message(1,"Как дела?!", userTwo)
       user.editMessage(userTwo,1,"Как дела?!")

    }
    @Test
    fun editMessageSuccess() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        val exceptionMessage = Message(1,"Как дела?!", userTwo)
        val result = user.editMessage(userTwo,1,"Как дела?!")
        assertEquals(exceptionMessage,result)
    }

    @Test
    fun deleteMessage() {
    }

    @Test
    fun countUnreadChats() {
    }

    @Test
    fun getUserChats() {
    }

    @Test
    fun deleteChat() {
    }

    @Test
    fun getLastMessagesFromChats() {
    }
}