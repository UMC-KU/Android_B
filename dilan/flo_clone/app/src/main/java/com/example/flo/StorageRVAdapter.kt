package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemStorageSongBinding

class StorageRVAdapter(private var data: ArrayList<MyStorageData>) :
    RecyclerView.Adapter<StorageRVAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemStorageSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myStorageData: MyStorageData) {
            binding.itemStorageSongIv.setImageResource(myStorageData.coverImg!!)
            binding.itemStorageSongTitleTv.text = myStorageData.title
            binding.itemStorageSongSingerTv.text = myStorageData.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStorageSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}