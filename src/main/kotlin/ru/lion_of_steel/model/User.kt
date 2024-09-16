package ru.lion_of_steel.model

import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.service.ChatService
import ru.lion_of_steel.service.ChatService.getAllChats
import ru.lion_of_steel.service.ChatService.getUnreadChatsCount

data class User(
    override val id: Int,
    var name: String,
    var chats: MutableMap<User, Chat> = mutableMapOf(),//вариация через Map
    var posts: List<Post>? = mutableListOf(),//потом перенести сюда посты
    var notes: List<Note>? = mutableListOf(),//потом перенести сюда заметки

) : Entity {
    fun pushMessage(userWhich: User, message: Message): Message {
        return chats.getOrPut(userWhich) {
            Chat(0, Pair(this, userWhich)).also { ChatService.add(it) }
        }.addMessage(message)
    }


    fun editMessage(userWhich: User, idMessage: Int, newTextMessage: String): Message {
        val chat = chats[userWhich] ?: throw NotFoundException("Чат с пользователем ${userWhich.name} не найден.")
        val message = chat.messages.find { it.id == idMessage }
            ?: throw NotFoundException("Сообщение с id: $idMessage не найдено")
        val updateMessage = message.copy(text = newTextMessage)
        chat.messages[chat.messages.indexOf(message)] = updateMessage
        return updateMessage
    }


    fun deleteMessage(userWhich: User, idMessage: Int): String {
        val chat = chats[userWhich] ?: throw NotFoundException("Чат с пользователем ${userWhich.name} не найден.")
        val message = chat.messages.find { it.id == idMessage }
            ?: throw NotFoundException("Сообщение с id: $idMessage не найдено")
        chat.messages.remove(message)
        return "Сообщение удалено"
    }


    fun countUnreadChats(): String {
        return "У вас ${this.getUnreadChatsCount()} непрочитанных чатов!"
    }

    fun getUserChats(): String {//получить список чатов
        return "Ваш список чатов: ${this.getAllChats()}"

    }

    fun deleteChat(userWhich: User): Boolean {
        return chats[userWhich]?.let { chat ->
            ChatService.delete(chat)
            true
        } ?: false

    }

    fun getLastMessagesFromChats(): Message? {
        if (chats.isEmpty()) throw NotFoundException("Список чатов пуст!")
        return chats.asSequence()
            .mapNotNull { (user, chat) ->
                chat.messages.lastOrNull { message -> message.sender != this && !message.readOrNot }
                    ?.also { println("Сообщения от $user: $it") }
            }
            .lastOrNull()
            ?: run {
                println("Непрочитанные сообщения не найдены")
                null


            }
    }

    fun getLastMessagesFromOneChat(idUser: Int, countMessage: Int = 10): List<Message> {
        val user = chats.keys.find { it.id == idUser } ?: throw throw NotFoundException("Пользователь не найден")
        val chat = chats[user] ?: throw NotFoundException("Чат не найден!")

        val result = chat.messages
            .asSequence()
            .filter { it.sender != this }
            .take(countMessage)
            .onEach { it.readOrNot = true }
            .toList()
        if (result.isEmpty()) throw NotFoundException("Нет сообщений")

        return result
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return name
    }

}
