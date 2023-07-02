package ru.netology.nmedia.dto

import ru.netology.nmedia.model.Track

data class TrackShort (
    val id: Long,
    val file: String,
) {
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/netology-code/andad-homeworks/master/09_multimedia/data/"
    }

        fun toModel() = Track(
            id = id,
            trackName = file,
            url = BASE_URL + file
        )
}