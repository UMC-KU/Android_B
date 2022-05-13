package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList:ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(album:Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position:Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding : ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //viewHolder에 bind를 해줘야 할 때마다 호출되는 함수   ex. 사용자가 화면을 위아래로 스크롤 할 때마다
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])

        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }

        //타이틀 누르면 삭제
       // holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    //
    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding:ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album:Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text=album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }

    }
}