package ru.lion_of_steel.service

import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.model.User

object ServiceUser {

    private var users = mutableListOf<User>()
    private var idUser = 0

    fun addUser(userType: User): User {

        users += userType.copy(id = ++idUser)
        return users.last()

    }

    fun getUsers(): List<User> {
        return users.ifEmpty { throw NotFoundException("Пользователей нет") }.toList()
    }

    fun getUser(user: User, userTwo: User): Boolean {
        return users.contains(user) && users.contains(userTwo) || throw NotFoundException("Один из пользователей не существует!")
    }

    fun clear() {
        users.clear()
        idUser = 0
    }
}