package ru.lion_of_steel.service

import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.model.User

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