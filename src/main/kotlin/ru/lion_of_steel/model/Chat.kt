package ru.lion_of_steel.model

import ru.lion_of_steel.exception.NotFoundException
//TODO
// TASK 7 редактировать их
// TASK 8 и удалять. Для простоты — можно удалять и свои, и чужие.
// TASK 9 В каждом чате есть прочитанные и непрочитанные сообщения.

//TODO для пользователя:
// TASK 10 Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount).
// В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
// TASK 11 Получить список чатов (например, service.getChats).
// TASK 12 Получить список последних сообщений из чатов (можно в виде списка строк). Если сообщений в чате нет (все были удалены), то пишется «нет сообщений».
// TASK 13 Получить список сообщений из чата, указав:
// ID собеседника;
// количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.
// TASK 14 Создать новое сообщение.
// TASK 15 Удалить сообщение.
// TASK 16 Создать чат. Чат создаётся, когда пользователю отправляется первое сообщение.
// TASK 17 Удалить чат, т. е. целиком удалить всю переписку.
// TASK 18 использовать lambda-функции (их напишите сами)
// TASK 19 и extension-функции (есть в составе Iterable, Collection, List).

//TODO Расчёт статистики производить как цепочку вызовов lambda-функций.
// Попробуйте обойтись без for, while и do-while.
// TASK 20 авто-тесты

data class Chat(
    override var id: Int,
    val pairUser: Pair<User, User>,
    private var messages: MutableList<Message> = mutableListOf<Message>()
) : Entity {
    private var messageIdCounter = 0
    fun addMessage(message: Message): Message {
        val newMessage = message.copy(id = ++messageIdCounter)
        messages.add(newMessage)
        return newMessage
    }
}

data class Message(
    override val id: Int = 0,
    val text: String?= "Это мое сообщение! ",
    var sender: User,//отправитель сообщения
    val readOrNot: Boolean = false,//прочитано или нет.
) : Entity

object ChatService {
    private var idChat = 0
    fun add(entity: Chat): Boolean {
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second
        if (ServiceUser.getUser(oneUser, twoUser)) {
            oneUser.chats[twoUser] = entity
            twoUser.chats[oneUser] = entity
            entity.id = ++idChat
            return true
        }
        return false//TODO после завершения задания модифицировать, так чтобы когда очищался чат у одного - другого не очищался.
    }

    fun delete(entity: Chat): Boolean {
        //TODO пока удаление будет происходить у всех
        val oneUser = entity.pairUser.first
        val twoUser = entity.pairUser.second
        if (ServiceUser.getUser(oneUser,twoUser)) {
            oneUser.chats.remove(twoUser)
            twoUser.chats.remove(oneUser)
            return true
        }
        return false
    }

    //TODO("Реализовать получения чатов определенного пользователя")
    fun getChats(user: User): List<Pair<User,Chat>> {

        val resultList = mutableListOf<Pair<User,Chat>>()

        for(entry in user.chats.entries) {
            if (user.chats.entries.isEmpty()) throw NotFoundException("У вас еще нет чатов!")
           val chatPair = Pair(entry.key,entry.value)
            resultList.add(chatPair)
        }
        return resultList
    }


}