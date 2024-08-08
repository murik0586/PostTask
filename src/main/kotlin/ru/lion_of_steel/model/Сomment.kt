package ru.lion_of_steel.model

import ru.lion_of_steel.attachment.Attachment
import java.time.LocalDateTime

data class Comment (
    var idComment: Int? = 0,//id комментария
    val fromId: Int? = 0,//id автора комментария
    val date: LocalDateTime? = LocalDateTime.now(),//дата
    var text: String? = " ",//текст комментария
    var attachment: List<Attachment>? = listOf(),
    var delete: Boolean = false,
)

data class ReportComment(
    val ownerId: Int = 0,// Идентификатор пользователя коммента(поле из класса Comment fromId)
    val commentId: Int = 0,//id комментария
    var reasonCode: Int = 0, //код причины жалобы
    var msg: String = "" //причина жалобы в текстовом виде
)
class Comments(
    var count: Int? = 0,//количество комментариев.
    val canPost: Boolean? = false,//может ли текущий пользователь комментировать.
    val canClose: Boolean? = false,//может ли текущий пользователь закрыть комментарии.
    val canOpen: Boolean? = false,//может ли текущий пользователь открыть комментарии.
    var commentList: List<Comment> = mutableListOf(),
)