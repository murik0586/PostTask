package ru.lion_of_steel.service

import ru.lion_of_steel.model.Chat
import ru.lion_of_steel.model.User

object ChatService {
    private var idChat = 0
    fun add(entity: Chat): Boolean {//создания чата
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second

        return ServiceUser.getUser(oneUser, twoUser).takeIf { it }?.let {
            oneUser.chats[twoUser] = entity
            twoUser.chats[oneUser] = entity
            entity.id = ++idChat
            true
        } ?: false
    }//создаем чат если оба пользователя существуют - чат добавляется двум пользователям

    fun delete(entity: Chat): Boolean {//удаления чата
        //пока удаление будет происходить у всех
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second
        return ServiceUser.getUser(oneUser, twoUser).takeIf { it }?.let {
            oneUser.chats.remove(twoUser)
            twoUser.chats.remove(oneUser)
            true
        } ?: false
        //takeIf - проверяет условие, т.е. что вызов ServiceUser.getUser - вернул true, тогда продолжаем выполнять
        // "(? .let)" -> если результат не нал, то выполняется логика за фигурными скобками, и возвращается true
        // ?: если takeIf вернет null - то возвращаем результат false,
    }

    fun User.getAllChats(): List<Pair<User, Chat>> {

        return this.chats.entries.map { Pair(it.key, it.value) }//получаем список чатов у определенного пользователя
    }

    fun User.getUnreadChatsCount(): Int {
        return this.chats.values.count { chat -> chat.messages.any { !it.readOrNot } }
        //тут мы получаем число чатов с непрочитанными сообщениями
    }

    fun clear() {
        idChat = 0
    }
}