package ru.lion_of_steel.model

import ru.lion_of_steel.attachment.Attachment
import java.time.LocalDateTime

data class Post(
    override val id: Int,// id поста
    val authorId: Int = 0,// id автора
    val ownerId: Int = 0, // id того, на чьей стене
    val date: LocalDateTime = LocalDateTime.now(), // время публикации
    val contentText: String = "Здесь какой-то контент",// Контент
    val friendsOnly: Boolean = false,// Только для друзей ли
    val comments: Comments = Comments(),// Комментарии
    val likes: Likes = Likes(),//лайки
    val reposts: Reposts = Reposts(),//репосты
    var attachment: List<Attachment>? = listOf(),//Чтобы не ругалось на hashCode
    val views: Views = Views(10),// просмотры
    val postType: String? = "post",//Тип записи, принимает значения: post, copy, reply, postpone, suggest.
    val canPin: Boolean? = false,//может ли текущий пользователь закрепить запись
    val canDelete: Boolean? = false,//может ли пользователь удалить запись
    val canEdit: Boolean? = false,//может ли текущий пользователь редактировать запись
    val isPinned: Boolean? = true,//Информация о том, что запись закреплена.
    val markedAsAds: Boolean? = false,//содержит ли запись отметку «реклама»
    val isFavorite: Boolean? = false,//добавлен в закладки у текущего пользователя.
    val postponedId: Boolean? = true,//Идентификатор отложенной записи.
    var delete: Boolean = false,
) : Entity

fun updateLikes(post: Post): Likes {
    val userLikes = post.likes.userLikes ?: false
    val canPublish = post.likes.canPublish ?: false

    return Likes(post.likes.count, userLikes, canPublish)
} // новая функция

class Comments(
    var count: Int? = 0,//количество комментариев.
    val canPost: Boolean? = false,//может ли текущий пользователь комментировать.
    val canClose: Boolean? = false,//может ли текущий пользователь закрыть комментарии.
    val canOpen: Boolean? = false,//может ли текущий пользователь открыть комментарии.
    var commentList: List<Comment> = mutableListOf(),
)


data class Likes(
    var count: Int = 0,//количество лайков.
    var userLikes: Boolean? = false,//наличие отметки «Мне нравится» от текущего пользователя
    var canPublish: Boolean? = false//может ли текущий пользователь сделать репост записи
) {
    override fun toString(): String {
        return String.format("понравилось: %d", count)
    }
}

class Reposts(
    private var count: Int = 0,//число пользователей, скопировавших запись;
    private val userReposted: Boolean = false,//наличие репоста от текущего пользователя
)

class Views(
    private var count: Int = 0// количество просмотров.
) {
    override fun toString(): String {
        return String.format("посмотрели: %d", count)
    }
}

data class ReportComment(
    val ownerId: Int = 0,// Идентификатор пользователя коммента(поле из класса Comment fromId)
    val commentId: Int = 0,//id комментария
    var reasonCode: Int = 0, //код причины жалобы
    var msg: String = "" //причина жалобы в текстовом виде
)

data class Comment(
    var idComment: Int? = 0,//id комментария
    val fromId: Int? = 0,//id автора комментария
    val date: LocalDateTime? = LocalDateTime.now(),//дата
    var text: String? = " ",//текст комментария
    var attachment: List<Attachment>? = listOf(),
    var delete: Boolean = false
)


