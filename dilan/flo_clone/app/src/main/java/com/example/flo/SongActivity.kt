package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    var mediaPlayer: MediaPlayer?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //XML내용을 가져와서 마음껏 쓰게 하는 것. 괄호 안에 XML의 ID를 넣어주어야 함.

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songPlayBtnIv.setOnClickListener {
            setPlayerStatus(false)
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)

            }
            mediaPlayer?.start()

        }

        binding.songPauseBtnIv.setOnClickListener {
            setPlayerStatus(true)
            mediaPlayer?.pause()

        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songMusicSingerTv.text = intent.getStringExtra("singer")
        }


        binding.songLikeOffIv.setOnClickListener {
            setSongLike(true)
        }

        binding.songLikeOnIv.setOnClickListener {
            setSongLike(false)
        }


        binding.songUnlikeOffIv.setOnClickListener {
            setSongUnLike(true)
        }

        binding.songUnlikeOnIv.setOnClickListener {
            setSongUnLike(false)
        }



    }

    fun setSongUnLike(unLike: Boolean) {
        if (unLike) {
            binding.songUnlikeOffIv.visibility = View.GONE
            binding.songUnlikeOnIv.visibility = View.VISIBLE
            Toast.makeText(this, "이 곡을 안 듣기 설정하였습니다.", Toast.LENGTH_SHORT).show()
        } else {
            binding.songUnlikeOffIv.visibility = View.VISIBLE
            binding.songUnlikeOnIv.visibility = View.GONE
            Toast.makeText(this, "안 듣기 설정을 해제하였습니다.", Toast.LENGTH_SHORT).show()

        }
    }


    fun setPlayerStatus(isPlaying: Boolean) {

        if (isPlaying) {
            binding.songPlayBtnIv.visibility = View.VISIBLE
            binding.songPauseBtnIv.visibility = View.GONE

        } else {
            binding.songPlayBtnIv.visibility = View.GONE
            binding.songPauseBtnIv.visibility = View.VISIBLE

        }
    }

    fun setSongLike(isLike: Boolean) {
        if (isLike) {
            binding.songLikeOffIv.visibility = View.GONE
            binding.songLikeOnIv.visibility = View.VISIBLE
            Toast.makeText(this, "좋아요 표시를 하였습니다.", Toast.LENGTH_SHORT).show()
        } else {
            binding.songLikeOffIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility = View.GONE
            Toast.makeText(this, "좋아요 표시를 해제하였습니다.", Toast.LENGTH_SHORT).show()

        }
    }


}