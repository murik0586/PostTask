package ru.lion_of_steel.attachment

interface Attachment {
    val type: String
}

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
) : Attachment

class AudioAttachment(audio: Audio) : Attachment {
    override val type: String = audio.type
}


class Video(
    override val type: String = "Video",
    val id: Int? = null,
    val ownerId: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val duration: Int? = null,
    val firstFrame: Array<Frame>? = null,//Содержит массив объектов Frane
    val views: Int? = null,
) : Attachment

class Frame( //Изображение первого кадра.
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)


class VideoAttachment(video: Video) : Attachment {
    override val type: String = video.type
}

class Photo(
    override val type: String = "Photo",
    val id: Int? = null,
    val albumId: Int? = null,
    val ownerId: Int? = null,
    val text: String? = null,
    val width: Int? = null,
    val height: Int? = null
) : Attachment

class PhotoAttachment(photo: Photo): Attachment {
    override val type  = photo.type
}

class File(
    override val type: String = "File",
    val id: Int? = null,
    val ownerId: Int? = null,
    val title: String? = null,
    val size: Int? = null, //размер файла в байтах
    val ext: String? = null,// расширение файла
    val url: String? = null,// адрес файла, по которому можно загрузить
) : Attachment

class FileAttachment(file: File): Attachment {
    override val type = file.type
}

class Link(
    override val type: String = "Link",
    val url: String? = null,
    val title: String? = null,
    val description: String? = null,
    val photo: Photo? = null,
) : Attachment

class LinkAttachment(link: Link) : Attachment {
    override val type = link.type
}
