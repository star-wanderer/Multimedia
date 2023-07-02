package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.model.Track
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
//import ru.netology.nmedia.repository.*
//import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Track(
    id = 0,
    likedByMe = false,
    trackName = "",
    albumName = "",
    skippedByMe = false,
    duration = "",
    url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3"
)

class TrackViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData<List<Track>?>()
    val data: LiveData<List<Track>?>
        get() = _data

    private val _albumName = MutableLiveData<String>()
    val albumName: LiveData<String>
        get() = _albumName

    private val _artistName = MutableLiveData<String>()
    val artistName: LiveData<String>
        get() = _artistName

    init {
        loadTracks()
    }

    fun toggle(track: Track) {
        _data.value = _data.value?.map {
            if (it.id == track.id) it.copy(isPlaying = !it.isPlaying) else it
        }
    }

    private fun loadTracks() {
        thread {
            try {
                val tracks = repository.getTracks().tracks.map {
                    it.toModel()
                }
            _data.postValue(tracks)
            _albumName.postValue(repository.getTracks().title)
            _artistName.postValue(repository.getTracks().artist)
            } catch (e: IOException) {
                throw Exception(e)
            }
        }
    }

//    fun like(post: Post) {
//        thread {
//            val likedPost = repository.like(post)
//            _data.postValue(
//                _data.value?.copy(posts = _data.value?.posts.orEmpty()
//                    .map { if (it.id == likedPost.id) likedPost else it }
//                )
//            )
//        }
//    }
//
}
