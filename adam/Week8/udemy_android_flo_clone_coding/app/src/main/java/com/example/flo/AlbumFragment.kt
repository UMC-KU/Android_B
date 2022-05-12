package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        //Home에서 넘어온 데이터를 반영

        isLiked = isLikedAlbum(album.id)
        setInit(album)
        initViewpager()
        setOnClickListeners(album)


        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text = album.title.toString()
        binding.albumSingerTv.text = album.singer.toString()
        if (isLiked) {
            binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }


    }

    private fun initViewpager() {
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()
    }

    //현재 로그인 되어있는 사람의 정보를 알기위해 jwt를 가져온다
    private fun getJwt():Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        //Fragment에서 sharedPreference 사용할때 이렇게 쓰면 된다.
        return spf!!.getInt("jwt",0)
    }

    //앨범 좋아요 눌렀을 때, DB에 저장해주는 함수
    private fun likedAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)
        //LikeTable에 정보 추가하기 위한 작업

        songDB.albumDao().likeAlbum(like)
    }

    //사용자가 앨범 좋아요 눌렀는지 판단하는 함수
    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
        //어떤 유저가 해당 앨범을 좋아요 했는지 확인해주는 변수

        return likeId != null
        //좋아요 눌렀으면 likeId가 null이 아니기 때문에 true
        //좋아요 누르지 않았으면 likeId가 null이기 때문에 false
    }

    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)

    }

    //좋아요 눌렀을 때 클릭되는것을 처리해주기 위한 함수
    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeOffIv.setOnClickListener {
            if(isLiked){
                //좋아요 눌러져있을 때 다시 누르는것은 좋아요 취소
                binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            } else {
                binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
                likedAlbum(userId, album.id)
            }
        }

        //back click
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()

        }

    }

}