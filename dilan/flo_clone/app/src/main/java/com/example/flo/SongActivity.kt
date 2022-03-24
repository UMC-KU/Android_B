package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //XML내용을 가져와서 마음껏 쓰게 하는 것. 괄호 안에 XML의 ID를 넣어주어야 함.

        binding.songDownIb.setOnClickListener{
            finish()
        }

        binding.songPlayBtnIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPauseBtnIv.setOnClickListener{
            setPlayerStatus(true)
        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songMusicSingerTv.text = intent.getStringExtra("singer")
        }

    }


    fun setPlayerStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songPlayBtnIv.visibility = View.VISIBLE
            binding.songPauseBtnIv.visibility = View.GONE
        }
        else{
            binding.songPlayBtnIv.visibility = View.GONE
            binding.songPauseBtnIv.visibility = View.VISIBLE
        }
    }



}