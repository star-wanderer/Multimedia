package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.TrackBinding
import ru.netology.nmedia.model.Track

interface OnInteractionListener {
    fun onLike (track: Track) {}
    fun onToggle (position: Int, trackList: List<Track>) {}
}

class TracksAdapter (
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Track,TrackViewHolder>(TackDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(position, currentList)
    }
}

class TrackViewHolder(
    private val binding: TrackBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, trackList: List<Track>) {
        binding.apply {
            trackName.text = trackList[position].trackName
            trackControl.isChecked = trackList[position].isPlaying
            trackControl.setOnClickListener {
                trackControl.isChecked = trackList[position].isPlaying
                onInteractionListener.onToggle(position,trackList)
            }
        }
    }
}

class TackDiffCallback : DiffUtil.ItemCallback<Track>(){
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
