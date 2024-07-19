package ru.lion_of_steel.attachment

sealed class Attachment(open val type: String)

    class Audio(
        override val type: String = "Audio",
        val id: Int? = null,
        val ownerId: Int? = null,
        val artist: String? = null,
        val title: String? = null,
        val duration: Int? = null,
        val url: String? = null,
        val noSearch: Boolean? = null,
        val isHd: Boolean? = false
    ) : Attachment(type)

    class AudioAttachment(audio: Audio) : Attachment(audio.type)


    class Video(
        override val type: String = "Video",
        val id: Int? = null,
        val ownerId: Int? = null,
        val title: String? = null,
        val description: String? = null,
        val duration: Int? = null,
        val firstFrame: Array<Frame>? = null,//Содержит массив объектов Frane
        val views: Int? = null,
    ) : Attachment(type)

    class Frame( //Изображение первого кадра.
        val height: Int? = null,
        val url: String? = null,
        val width: Int? = null
    )


    class VideoAttachment(video: Video) : Attachment(video.type)

    class Photo(
        override val type: String = "Photo",
        val id: Int? = null,
        val albumId: Int? = null,
        val ownerId: Int? = null,
        val text: String? = null,
        val width: Int? = null,
        val height: Int? = null
    ) : Attachment(type)

    class PhotoAttachment(photo: Photo) : Attachment(photo.type)
    class File(
        override val type: String = "File",
        val id: Int? = null,
        val ownerId: Int? = null,
        val title: String? = null,
        val size: Int? = null, //размер файла в байтах
        val ext: String? = null,// расширение файла
        val url: String? = null,// адрес файла, по которому можно загрузить
    ) : Attachment(type)

    class FileAttachment(file: File) : Attachment(file.type)

    class Link(
        override val type: String = "Link",
        val url: String? = null,
        val title: String? = null,
        val description: String? = null,
        val photo: Photo? = null,
    ) : Attachment(type)

    class LinkAttachment(link: Link) : Attachment(link.type)
