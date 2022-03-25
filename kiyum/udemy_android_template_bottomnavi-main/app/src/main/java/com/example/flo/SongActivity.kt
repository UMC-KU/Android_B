package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songPannelBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songPannelPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPannelPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songPannelTitleTv.text=intent.getStringExtra("title")
            binding.songPannelSingerTv.text=intent.getStringExtra("singer")
        }
    }
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPannelPlayIv.visibility = View.VISIBLE
            binding.songPannelPauseIv.visibility = View.GONE
        }
        else{
            binding.songPannelPlayIv.visibility = View.GONE
            binding.songPannelPauseIv.visibility = View.VISIBLE
        }
    }
}