package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Album

interface PostRepository {
    fun getTracks(): Album
}