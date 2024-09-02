package ru.lion_of_steel.service

import ru.lion_of_steel.model.Chat
import ru.lion_of_steel.model.User

object ChatService {
    private var idChat = 0
    fun add(entity: Chat): Boolean {//создания чата
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second
        if (ServiceUser.getUser(oneUser, twoUser)) {
            oneUser.chats[twoUser] = entity
            twoUser.chats[oneUser] = entity
            entity.id = ++idChat
            return true
        }
        return false
    }

    fun delete(entity: Chat): Boolean {//удаления чата
        //пока удаление будет происходить у всех
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second
        if (ServiceUser.getUser(oneUser, twoUser)) {
            oneUser.chats.remove(twoUser)
            twoUser.chats.remove(oneUser)
            return true
        }
        return false
    }
    fun User.getAllChats(): List<Pair<User, Chat>> {

        return this.chats.entries.map { Pair(it.key, it.value) }
    }

    fun User.getUnreadChatsCount(): Int {
        return this.chats.values.count { chat -> chat.messages.any { !it.readOrNot } }
    }

    fun clear() {

        idChat = 0
    }
}