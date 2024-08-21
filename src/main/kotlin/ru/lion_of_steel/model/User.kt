package ru.lion_of_steel.model

import ru.lion_of_steel.exception.NotFoundException

//TODO это версия где все это еще не храниться в общем сервисе!После реализации
// этого подхода - попробовать другой, где все храниться в сервисах
data class User(
    override val id: Int,
    var name: String,
    var chats: MutableMap<User, Chat> = mutableMapOf(),//вариация через Map
    var posts: List<Post>? = mutableListOf(),//потом перенести сюда посты
    var notes: List<Note>? = mutableListOf(),//потом перенести сюда заметки

) : Entity {
    fun pushMessage(userWhich: User, message: Message) {
        val chatUsers = chats.getOrPut(userWhich) {
            val newChat = Chat(0, Pair(this, userWhich))
            ChatService.add(newChat)
            newChat
        }
        chatUsers.addMessage(message)
    }
    fun editMessage() {

    }
    fun deleteMessage() {

    }
}


object ServiceUser {
    private var users = mutableListOf<User>()
    private var idUser = 0
    fun addUser(user: User): User {
        users += user.copy(id = ++idUser)
        return users.last()
    }

    fun getUsers(): List<User> {
        val resultUsersList = mutableListOf<User>()
        if (users.isEmpty()) throw NotFoundException("Пользователей нет")
        for (user in users) {
            resultUsersList.add(user)
        }
        return resultUsersList
    }

    fun getUser(user: User, userTwo: User): Boolean {
        if (users.contains(user) && users.contains(userTwo)) return true
        else throw NotFoundException("Один из пользователей не существует!")
    }

    fun clear() {
        users.clear()
        idUser = 0
    }
}

fun main() {
    val user = ServiceUser.addUser(User(1, "Мурат"))
    val userTwo = ServiceUser.addUser(User(2, "Тимур"))
    user.pushMessage(userTwo,Message(1,sender = user))
}