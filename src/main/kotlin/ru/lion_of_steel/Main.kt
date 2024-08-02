package ru.lion_of_steel

import ru.lion_of_steel.attachment.*
import ru.lion_of_steel.exception.PostNotFoundException
import ru.lion_of_steel.exception.ReasonNotReportException
import ru.lion_of_steel.exception.ReportNoOwnerAndCommentIdException

import java.time.LocalDateTime

data class Post(
    val idPost: Int,// id поста
    val authorId: Int = 0,// id автора
    val ownerId: Int = 0, // id того, на чьей стене
    val date: LocalDateTime = LocalDateTime.now(), // время публикации
    val contentText: String = "Здесь какой-то контент",// Контент
    val friendsOnly: Boolean = false,// Только для друзей ли
    val comments: Comments? = Comments(),// Комментарии
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
    val postponedId: Boolean? = true//Идентификатор отложенной записи.
)

fun updateLikes(post: Post): Likes {
    val userLikes = post.likes.userLikes ?: false
    val canPublish = post.likes.canPublish ?: false

    return Likes(post.likes.count, userLikes, canPublish)
} // новая функция

class Comments(
    private var count: Int? = 0,//количество комментариев.
    private val canPost: Boolean? = false,//может ли текущий пользователь комментировать.
    private val canClose: Boolean? = false,//может ли текущий пользователь закрыть комментарии.
    private val canOpen: Boolean? = false//может ли текущий пользователь открыть комментарии.

)


class Likes(
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
    val text: String? = " ",//текст комментария
    var attachment: List<Attachment>? = listOf()
)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<ReportComment>()
    private var postId = 0

    fun addReportComment(idComment: Int, fromId: Int, reason: Int): ReportComment {
        for (comment in comments) {
            if (comment.idComment == idComment && comment.fromId == fromId) {
                val msg = when (reason) {
                    0 -> "Спам"
                    1 -> "Детская порнография"
                    2 -> "Экстремизм"
                    3 -> "Насилие"
                    4 -> "Пропаганда наркотиков"
                    5 -> "Материал для взрослых"
                    6 -> "Оскорбление"
                    7 -> "Призывы к суициду."
                    else -> throw ReasonNotReportException("Такой причины не существует")

                }
                val reportComment = ReportComment(fromId,idComment,reason,msg)
                reports += reportComment.copy()
                return reportComment
            }
        }
        throw ReportNoOwnerAndCommentIdException("Несуществующий комментарий, возможно комментарий уже удален")
    }

    fun createComment(idPost: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.idPost == idPost) {
                comments += comment.copy()
                return comment
            }
        }
        throw PostNotFoundException("Пост с id: $idPost не найден")
    }

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
        comments = emptyArray()
        postId = 0
    }
}

fun main() {
    val photo = Photo(id = 1, albumId = 1, ownerId = 1, text = "A photo", width = 800, height = 600)
    val video = Video(
        id = 1,
        ownerId = 1,
        title = "A Funny Video",
        description = "This is a funny video",
        duration = 30
    )
    val audio = Audio(id = 1, ownerId = 1, artist = "Artist Name", title = "Track Title", duration = 180)
    val file = File(
        id = 1,
        ownerId = 1,
        title = "Document Title",
        size = 1024,
        ext = "pdf",
        url = "https://vk.com/some_doc_link"
    )
    val link = Link(
        url = "https://example.com",
        title = "Example Link",
        description = "Example Description"
    )
    var post = Post(
        1, 1, 1, postType = "post", likes = Likes(2000), attachment = listOf(
            PhotoAttachment(photo),
            AudioAttachment(audio),
            VideoAttachment(video)
        )
    )
    post = post.copy(likes = updateLikes(post))
    println(post)

}
