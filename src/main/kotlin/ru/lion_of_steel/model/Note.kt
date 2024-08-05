package ru.lion_of_steel.model

data class Note(
    var id: Int,
    var ownerId: Int,//идентификатор владельца
    var title: String,//заголовок заметки
    var text: String,//текст заметки
    //потом добавить по необходимости еще свойств!

)
//TODO нам придется возможно Реализовать POST и NOTE - от одного интерфейса. Или от абстрактного класса. Но я использую интерфейс.