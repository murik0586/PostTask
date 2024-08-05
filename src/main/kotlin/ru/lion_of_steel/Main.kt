package ru.lion_of_steel

import ru.lion_of_steel.attachment.*
import ru.lion_of_steel.model.Likes
import ru.lion_of_steel.model.Post
import ru.lion_of_steel.model.updateLikes


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
