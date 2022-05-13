package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerSongBinding

class LockerRVAdapter(private val lockerList:ArrayList<Album>):RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAdapter.ViewHolder {
        val binding : ItemLockerSongBinding = ItemLockerSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {
        holder.bind(lockerList[position])


    }

    override fun getItemCount(): Int = lockerList.size

    inner class ViewHolder(val binding: ItemLockerSongBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemLockerTitleTv.text = album.title
            binding.itemLockerSingerTv.text=album.singer
            binding.itemLockerCoverImgIv.setImageResource(album.coverImg!!)
        }

    }


}