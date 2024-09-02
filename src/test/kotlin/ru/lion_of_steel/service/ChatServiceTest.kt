package ru.lion_of_steel.service

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.model.Chat
import ru.lion_of_steel.model.Message
import ru.lion_of_steel.model.User
import ru.lion_of_steel.service.ChatService.getAllChats
import ru.lion_of_steel.service.ChatService.getUnreadChatsCount

class ChatServiceTest {
@Before
fun clear(){
    ChatService.clear()
    ServiceUser.clear()
}
    @Test
    fun addChatTrue() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        ServiceUser.addUser(user2)
        val newChat = Chat(0, Pair(user1, user2))

        val result = ChatService.add(newChat)
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun addChatFalse() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        val newChat = Chat(0, Pair(user1, user2))

        ChatService.add(newChat)
        //вместо false - ошибка происходит в методе внутри метода, выбрасывается исключение

    }

    @Test
    fun deleteTrue() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        ServiceUser.addUser(user2)
        val newChat = Chat(0, Pair(user1, user2))
        val result = ChatService.delete(newChat)

        assertTrue(result)

    }

    @Test(expected = NotFoundException::class)
    fun deleteException() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        val newChat = Chat(0, Pair(user1, user2))
        ChatService.delete(newChat)


    }

    @Test
    fun getChatsReturnList() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        ServiceUser.addUser(user2)
        val user3 = User(3, "Name3")
        ServiceUser.addUser(user3)
        val chat1 = Chat(1, Pair(user1, user2))
        ChatService.add(chat1)
        val chat2 = Chat(2, Pair(user1, user3))
        ChatService.add(chat2)
        val exception = mutableListOf(Pair(user2, chat1), Pair(user3, chat2))
        val result = user1.getAllChats()


        assertEquals(exception, result)
    }



    @Test
    fun getUnreadChatsCount() {
        val user1 = User(1, "Name1")
        ServiceUser.addUser(user1)
        val user2 = User(2, "Name2")
        ServiceUser.addUser(user2)
        val user3 = User(3, "Name3")
        ServiceUser.addUser(user3)
        user2.pushMessage(user1, Message(1,"Привет!",user2))
        user3.pushMessage(user1, Message(1,"Привет!",user2))
        val result = user1.getUnreadChatsCount()

        assertEquals(2, result)
    }


}