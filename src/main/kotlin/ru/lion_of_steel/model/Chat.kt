package ru.lion_of_steel.model


//TODO СООБЩЕНИЯ:
//TODO для пользователя:
// TASK 18 использовать lambda-функции (их напишите сами)
// TASK 19 и extension-функции (есть в составе Iterable, Collection, List).

//TODO Расчёт статистики производить как цепочку вызовов lambda-функций.
// Попробуйте обойтись без for, while и do-while.
// TASK 20 авто-тесты

data class Chat(
    override var id: Int,
    val pairUser: Pair<User, User>,
    var messages: MutableList<Message> = mutableListOf()
) : Entity {
    private var messageIdCounter = 0
    fun addMessage(message: Message): Message {
        val newMessage = message.copy(id = ++messageIdCounter)
        messages.add(newMessage)
        return newMessage
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chat

        if (id != other.id) return false
        if (pairUser != other.pairUser) return false
        if (messageIdCounter != other.messageIdCounter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + id.hashCode()
        result = 31 * result + pairUser.hashCode()
        result = 31 * result + messageIdCounter
        return result
    }

    override fun toString(): String {
        return "Чат ${pairUser.first} и ${pairUser.second}"
    }
}

data class Message(
    override val id: Int = 0,
    val text: String? = "Это мое сообщение! ",
    var sender: User,//отправитель сообщения
    var readOrNot: Boolean = false,//прочитано или нет.
) : Entity

