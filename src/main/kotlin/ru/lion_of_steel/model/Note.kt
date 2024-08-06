package ru.lion_of_steel.model

data class Note(
    override var id: Int,
    var ownerId: Int,//идентификатор владельца
    var title: String,//заголовок заметки
    var text: String,//текст заметки
    //потом добавить по необходимости еще свойств!

) : Entity
