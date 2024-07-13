import java.time.LocalDateTime

/*
TODO ФУНКЦИЯ СОЗДАНИЯ ЗАПИСИ
TODO функция обновления записи
TODO WALL TESTS:
 1.На функцию добавления записи.
  *который проверяет, что после добавления поста id стал отличным от 0
 2.На функцию обновления записи.
  *изменяем пост с существующим id, возвращается true,
  *изменяем пост с несуществующим id, возвращается false
TODO Refactoring и улучшения.
 */
data class Post(
    val idPost: Int,// id поста
    val authorId: Int,// id автора
    val ownerId: Int, // id того, на чьей стене
    val date: LocalDateTime = LocalDateTime.now(), // время публикации
    val contentText: String = "Здесь какой-то контент",// Контент
    val friendsOnly: Boolean = false,// Только для друзей ли
    val comments: Comments = Comments(),// Комментарии
    val likes: Likes = Likes(),//лайки
    val reposts: Reposts = Reposts(),//репосты
    val views: Views = Views(),// просмотры
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
    val count: Int = 0,//количество комментариев.
    val canPost: Boolean = true,//может ли текущий пользователь комментировать.
    val canClose: Boolean = false,//может ли текущий пользователь закрыть комментарии.
    val canOpen: Boolean = false//может ли текущий пользователь открыть комментарии.
)

class Likes(
    val count: Int = 0,//количество лайков.
    val userLikes: Boolean = false,//наличие отметки «Мне нравится» от текущего пользователя
    val canPublish: Boolean = false//может ли текущий пользователь сделать репост записи
)

class Reposts(
    private val count: Int = 0,//число пользователей, скопировавших запись;
    private val userReposted: Boolean = false,//наличие репоста от текущего пользователя
)

class Views(
    private val count: Int = 0// количество просмотров.
)

object WallService {
    private var posts = emptyArray<Post>();

    fun postAdd(post: Post): Post {
        TODO("Завершить")
        return post

    }
    fun update(idPost: Int): Boolean {
        TODO("Завершить")

    }

}

fun main() {

}