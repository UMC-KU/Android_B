package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedsongBinding

class SavedSongFragment : Fragment() {

    lateinit var binding : FragmentSavedsongBinding

    //private var lockerDatas = ArrayList<Album>()

    lateinit var songDB:SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedsongBinding.inflate(inflater,container,false)

        songDB = SongDatabase.getInstance(requireContext())!!

        //데이터 리스트 생성 더미 데이터
//        lockerDatas.apply {
//            add(Album("Butter", "방탄소년단 (BTS)",R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)",R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)",R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)",R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)",R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)",R.drawable.img_album_exp6))
//
//        }

        //어댑터 연결 및 레이아웃 매니저 설정
//        val lockerRVAdapter = LockerRVAdapter(lockerDatas)
//        binding.lockerSavedMusicRv.adapter = lockerRVAdapter
//        binding.lockerSavedMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedMusicRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object:SavedSongRVAdapter.MyItemCLickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }

        })

        binding.lockerSavedMusicRv.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}