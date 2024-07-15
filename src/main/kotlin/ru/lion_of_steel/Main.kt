package ru.lion_of_steel

import java.time.LocalDateTime

data class Post(
    val idPost: Int,// id поста
    val authorId: Int = 0,// id автора
    val ownerId: Int = 0, // id того, на чьей стене
    val date: LocalDateTime = LocalDateTime.now(), // время публикации
    val contentText: String = "Здесь какой-то контент",// Контент
    val friendsOnly: Boolean = false,// Только для друзей ли
    val comments: Comments = Comments(),// Комментарии
    val likes: Likes = Likes(),//лайки
    val reposts: Reposts = Reposts(),//репосты
    val views: Views = Views(10),// просмотры
    val postType: String = "Репост",//Тип записи, принимает значения: post, copy, reply, postpone, suggest.
    val canPin: Boolean = false,//может ли текущий пользователь закрепить запись
    val canDelete: Boolean = false,//может ли пользователь удалить запись
    val canEdit: Boolean = false,//может ли текущий пользователь редактировать запись
    val isPinned: Boolean = false,//Информация о том, что запись закреплена.
    val markedAsAds: Boolean = false,//содержит ли запись отметку «реклама»
    val isFavorite: Boolean = false,//добавлен в закладки у текущего пользователя.
    val postponedId: Boolean = false //Идентификатор отложенной записи.
)

class Comments(
    private var count: Int = 0,//количество комментариев.
    private val canPost: Boolean = true,//может ли текущий пользователь комментировать.
    private val canClose: Boolean = false,//может ли текущий пользователь закрыть комментарии.
    private val canOpen: Boolean = false//может ли текущий пользователь открыть комментарии.
)

class Likes(
    private var count: Int = 0,//количество лайков.
    private val userLikes: Boolean = false,//наличие отметки «Мне нравится» от текущего пользователя
    private val canPublish: Boolean = false//может ли текущий пользователь сделать репост записи
)

class Reposts(
    private var count: Int = 0,//число пользователей, скопировавших запись;
    private val userReposted: Boolean = false,//наличие репоста от текущего пользователя
)

class Views(
    private var count: Int = 0// количество просмотров.
)

object WallService {
    private var posts = emptyArray<Post>();
    private var postId = 0

    fun postAdd(post: Post): Post {
        posts += post.copy(idPost = ++postId)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, postFromPosts) in posts.withIndex()) {
            if (postFromPosts.idPost == post.idPost) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        postId = 0
    }
}

fun main() {

}