package com.example.flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {

    private lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB:SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.storageListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val savedSongRVAdapter = SavedSongRVAdapter()

        savedSongRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })
        binding.storageListRv.adapter = savedSongRVAdapter
        savedSongRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)

    }

}