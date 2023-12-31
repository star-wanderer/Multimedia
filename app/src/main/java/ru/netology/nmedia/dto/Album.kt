package ru.netology.nmedia.dto

data class Album (
    val id: Long = 0L,
    val title: String = "",
    val subtitle: String = "",
    val artist: String = "",
    val published: String = "",
    val genre: String = "",
    val tracks: List<TrackShort>,
)