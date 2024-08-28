package ru.lion_of_steel.service

import ru.lion_of_steel.exception.NotFoundException
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

    fun getChats(user: User): List<Pair<User, Chat>> {//получение чатов

        val resultList = mutableListOf<Pair<User, Chat>>()
        if (user.chats.entries.isEmpty()) throw NotFoundException("У вас еще нет чатов!")
        for (entry in user.chats.entries) {
            val chatPair = Pair(user, entry.value)
            resultList.add(chatPair)
        }
        return resultList
    }

    fun getUnreadChatsCount(user: User): Int {//проверка сколько непрочитанных чатов у пользователя
        var unreadChatsCount = 0
        if (user.chats.isEmpty()) throw NotFoundException("У вас еще нет чатов!")
        for (chat in user.chats.values) {
            for (message in chat.messages) {
                if (!message.readOrNot) {
                    unreadChatsCount++
                    break
                }
            }

        }
        return unreadChatsCount
    }

    fun clear() {

        idChat = 0
    }
}