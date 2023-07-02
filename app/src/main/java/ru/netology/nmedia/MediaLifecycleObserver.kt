package ru.netology.nmedia

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MediaLifecycleObserver : LifecycleEventObserver {
    var player: MediaPlayer? = MediaPlayer()
    private var position: Int? = 0
    private var trackUrl: String = ""

    fun onPause(): Boolean {
        return position!! > 0
    }

    fun pause(){
        player?.pause()
        position = player?.currentPosition
        player?.reset()
    }

    fun play(url: String){
        trackUrl = url
        player?.setDataSource(trackUrl)
        player?.setOnPreparedListener {
            it.start()
        }
        player?.prepareAsync()
    }

    fun stop(){
        player?.reset()
        position = 0
        trackUrl = ""
    }

    fun resume(){
        player?.setDataSource(trackUrl)
        player?.setOnPreparedListener {
            position?.let { it1 -> it.seekTo(it1) }
            it.start()
        }
        player?.prepareAsync()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> player?.pause()
            Lifecycle.Event.ON_STOP -> {
                player?.release()
                player = null
            }
            Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
            else -> Unit
        }
    }
}
