package com.example.flo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songDownarrowIv.setOnClickListener {
            finish()
        }
        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }

    }

    fun setPlayerStatus(isPlaying : Boolean) {
        if(isPlaying) {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        }
        else {
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        }
    }
}




