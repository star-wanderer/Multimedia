package ru.netology.nmedia.model

data class Track (
    val id: Long = 0L,
    val trackName: String = "",
    val albumName: String = "",
    val skippedByMe: Boolean = false,
    val likedByMe: Boolean = false,
    val isPlaying: Boolean = false,
    val duration: String = "",
    val file: String = "",
    val url: String = "",
)
