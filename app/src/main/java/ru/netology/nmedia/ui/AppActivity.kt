package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.netology.nmedia.R
import ru.netology.nmedia.MediaLifecycleObserver
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.TracksAdapter
import ru.netology.nmedia.databinding.ActivityAppBinding
import ru.netology.nmedia.model.Track
import ru.netology.nmedia.viewmodel.TrackViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    private val mediaObserver = MediaLifecycleObserver()
    private var previousPosition = 0
    private var currentTrack = Track()

    private val viewModel: TrackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TracksAdapter(object: OnInteractionListener {

            override fun onToggle(position: Int, trackList: List<Track>) {
                val nextPosition = if (position < trackList.lastIndex) position + 1 else 0
                mediaObserver.apply {
                    if (!player?.isPlaying!!) {
                        if (!this.onPause()) {
                            this.play(trackList[position].url)
                        } else {
                            if (position == previousPosition) {
                                this.resume()
                            } else {
                                this.stop()
                                this.play(trackList[position].url)
                            }
                        }
                    } else {
                        if (position != previousPosition){
                            this.stop()
                            viewModel.toggle(trackList[previousPosition])
                            this.play(trackList[position].url)
                        } else {
                            this.pause()
                        }
                    }
                    viewModel.toggle(trackList[position])
                    currentTrack = trackList[position]

                    player?.setOnCompletionListener {
                        player?.reset()
                        viewModel.toggle(trackList[position])
                        onToggle(nextPosition, trackList)
                    }
                }
                previousPosition = position
                println("Playing now: ${trackList[position].url}")
                println("Playing next: ${trackList[nextPosition].url}")
            }
        })

        binding.trackControl.setOnClickListener{
            if (currentTrack.id !=0L){
                viewModel.toggle(currentTrack)
                mediaObserver.apply {
                    if (this.player?.isPlaying!!) this.pause() else this.resume()
                }
            }
        }

        binding.list.adapter = adapter

        viewModel.data.observe(this) { it ->
            val list = it?.filter { it.isPlaying }
                if (list?.isEmpty()!!){
                    binding.trackControl.isChecked = false
                } else {
                    binding.trackControl.isChecked = true
                    binding.trackName.text = list.last().trackName
                }
            adapter.submitList(it)
        }

        viewModel.albumName.observe(this){
            binding.albumName.text = it
        }

        viewModel.artistName.observe(this){
            binding.artistName.text = it
        }
    }
}



