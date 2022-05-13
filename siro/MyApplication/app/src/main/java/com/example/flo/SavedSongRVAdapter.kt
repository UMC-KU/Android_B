package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerSongBinding

class SavedSongRVAdapter() : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>(){

    private val songs = ArrayList<Song>()
    interface MyItemCLickListener {
        fun onRemoveSong(songId:Int)
    }
    private lateinit var mItemClickListener: MyItemCLickListener

    fun setMyItemClickListener(itemCLickListener: MyItemCLickListener){
        mItemClickListener = itemCLickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SavedSongRVAdapter.ViewHolder {
        val binding: ItemLockerSongBinding = ItemLockerSongBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)

        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs:ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position:Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerSongBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song:Song){
            binding.itemLockerCoverImgIv.setImageResource(song.coverImg!!)
            binding.itemLockerTitleTv.text=song.singer
            binding.itemLockerSingerTv.text = song.singer
        }

    }
}