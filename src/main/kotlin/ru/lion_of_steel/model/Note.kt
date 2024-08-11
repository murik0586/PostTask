package ru.lion_of_steel.model

data class Note(
    override var id: Int = 0,
    var ownerId: Int= 0,//идентификатор владельца
    var title: String? = null,//заголовок заметки
    var text: String? = null,//текст заметки
    var delete:Boolean = false,
    var comments: Comments = Comments()

) : Entity
