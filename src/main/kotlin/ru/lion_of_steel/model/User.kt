package ru.lion_of_steel.model

import ru.lion_of_steel.exception.NotFoundException
import ru.lion_of_steel.service.ChatService

data class User(
    override val id: Int,
    var name: String,
    var chats: MutableMap<User, Chat> = mutableMapOf(),//вариация через Map
    var posts: List<Post>? = mutableListOf(),//потом перенести сюда посты
    var notes: List<Note>? = mutableListOf(),//потом перенести сюда заметки

) : Entity {
    fun pushMessage(userWhich: User, message: Message): Message {
        val chatUsers = chats.getOrPut(userWhich) {
            val newChat = Chat(0, Pair(this, userWhich))
            ChatService.add(newChat)
            newChat
        }
        return chatUsers.addMessage(message)
    }

    fun editMessage(userWhich: User, idMessage: Int, newTextMessage: String): Message {
        val chat = chats[userWhich] ?: throw NotFoundException("Чат с пользователем ${userWhich.name} не найден.")
        for ((index, message) in chat.messages.withIndex()) {
            if (message.id == idMessage) {
                chat.messages[index] = message.copy(text = newTextMessage)
                return chat.messages.last()
            }
        }
        throw NotFoundException("Сообщение с id: $idMessage не найдено")
    }

    fun deleteMessage(userWhich: User, idMessage: Int): String {
        val chat = chats[userWhich] ?: throw NotFoundException("Чат с пользователем ${userWhich.name} не найден.")
        val messageIterator = chat.messages.iterator()
        while (messageIterator.hasNext()) {
            val message = messageIterator.next()
            if (message.id == idMessage) {
                messageIterator.remove()
                return "Сообщение удалено"
            }
        }
        throw NotFoundException("Сообщение с ID $idMessage не найдено")
    }


    fun countUnreadChats(): String {
        return "У вас ${ChatService.getUnreadChatsCount(this)} непрочитанных чатов!"
    }

    fun getUserChats(): String {//получить список чатов
        return "Ваш список чатов: ${ChatService.getChats(this)}"

    }

    fun deleteChat(userWhich: User): Boolean {
        val chatDelete = chats[userWhich]
        if (chatDelete != null) {
            ChatService.delete(chatDelete)
            return true
        }
        return false
    }

    fun getLastMessagesFromChats(): Message? {

        if (chats.isEmpty()) throw NotFoundException("Список чатов пуст!")

        for (chat in chats) {
            val nameUser = chat.key
            val chatUser = chat.value.messages

            if (chatUser.isEmpty()) {
                println("Нет сообщений от $nameUser")
            } else {
                println("Сообщения от $nameUser: ")
                for (message in chatUser.reversed()) {
                    if (message.sender != this && !message.readOrNot) {
                        return message

                    }
                }
                println("Нет непрочитанных сообщений от $nameUser")
            }
        }
        println("Непрочитанные сообщения не найдены")
        return null
    }

    fun getLastMessagesFromOneChat(idUser: Int, countMessage: Int = 10): List<Message> {
        val listResult = mutableListOf<Message>()
        var count = 0

        for (chat in chats) {
            val nameUser = chat.key
            val chatUser = chat.value.messages

            if (nameUser.id == idUser) {
                if (chatUser.isEmpty()) {
                    println("Нет новых сообщений")
                } else {
                    println("Сообщения от $nameUser: ")

                    for (message in chatUser) {
                        if (message.sender != this) {
                            listResult.add(message)
                            count++
                            if (count>= countMessage) break
                        }
                    }
                }
            }
        }
        for (message in listResult) {
            message.readOrNot = true
        }
        return listResult
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
