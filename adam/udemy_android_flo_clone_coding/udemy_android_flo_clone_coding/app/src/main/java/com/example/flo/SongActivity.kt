package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

//    var mediaPlayer:MediaPlayer?=null
//    var vol = 0.5f

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initLayout()

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
            binding.songBtnPauseIv.visibility = View.INVISIBLE
        }
        else {
            binding.songBtnPlayIv.visibility = View.INVISIBLE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        }
    }

//    fun initLayout() {
//        val playBtn = findViewById<ImageView>(R.id.song_btn_play_iv)
//        playBtn.setOnClickListener {
//            if (mediaPlayer == null) {
//                mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)
//                mediaPlayer?.setVolume(vol, vol)
//            }
//            mediaPlayer?.start()
//        }
//
//        val pauseBtn = findViewById<ImageView>(R.id.song_btn_pause_iv)
//        pauseBtn.setOnClickListener {
//            mediaPlayer?.pause()
//        }
//
//    }


}




