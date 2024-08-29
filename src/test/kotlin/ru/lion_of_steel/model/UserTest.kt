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

    @Test(expected = NotFoundException::class)
    fun deleteMessageNotChat() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        user.deleteMessage(userTwo,1)

    }
    @Test
    fun deleteMessageSuccess() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        user.pushMessage(userTwo, message)
        val expected = "Сообщение удалено"
        val result = user.deleteMessage(userTwo,1)
        assertEquals(expected,result)
    }
    @Test(expected = NotFoundException::class)
    fun deleteMessageNotMessage() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        user.deleteMessage(userTwo,2)
    }


    @Test
    fun countUnreadChatsSuccess() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        val exceptionResult = "У вас 1 непрочитанных чатов!"
        val result = user.countUnreadChats()
        assertEquals(exceptionResult,result)
    }

    @Test
    fun getUserChats() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        val chat1 = Chat(1, Pair(user, userTwo))
        ChatService.add(chat1)
        user.pushMessage(userTwo, message)
        val expectList = mutableListOf(Pair(user, chat1))
        val expected = "Ваш список чатов: $expectList"
        val result = user.getUserChats()
        assertEquals(expected,result)
    }

    @Test
    fun deleteChatTrue() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        user.pushMessage(userTwo, message)
        val result = user.deleteChat(userTwo)
        assertTrue(result)
    }
    @Test
    fun deleteChatFalse() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val result = user.deleteChat(userTwo)
        assertFalse(result)
    }

    @Test
    fun getLastMessagesFromChatsSuccess() {//переделать
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val message = Message(1, "Привет!", userTwo)
        val chat1 = Chat(1, Pair(user, userTwo))
        ChatService.add(chat1)
        user.pushMessage(userTwo, message)
        val expected = Message(id = 1, text = "Привет!", sender = userTwo, false)
        val result = user.getLastMessagesFromChats()
        assertEquals(expected,result)
    }
    @Test(expected = NotFoundException::class)
    fun getLastMessagesFromChatsNotChats() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        user.getLastMessagesFromChats()

    }
    @Test
    fun getLastMessagesFromChatsNull() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
       Message(1, "Привет!", userTwo)
        val chat1 = Chat(1, Pair(user, userTwo))
        ChatService.add(chat1)
        val result = user.getLastMessagesFromChats()
        assertEquals(null,result)
    }
    @Test
    fun getLastMessagesFromOneChatSuccess() {
        val user = User(1, "Мурат")
        val userTwo = User(2, "Тимур")
        serviceUser.addUser(user)
        serviceUser.addUser(userTwo)
        val messageOne = Message(1, "Привет!", userTwo)
        val messageTwo = Message(2, "Как дела?!", userTwo)
        val chat1 = Chat(1, Pair(user, userTwo))
        ChatService.add(chat1)
        user.pushMessage(userTwo, messageOne)
        user.pushMessage(userTwo,messageTwo)

        val expected = mutableListOf(messageOne.copy(readOrNot = true),messageTwo.copy(readOrNot = true))
        val result = user.getLastMessagesFromOneChat(2,10)

        assertEquals(expected,result)
    }
    @Test
    fun getLastMessagesFromOneChat() {
    }
}