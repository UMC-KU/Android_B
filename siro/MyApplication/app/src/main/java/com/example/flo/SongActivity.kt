package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songBtnDownIb.setOnClickListener{
            finish()
        }
        binding.songPlayBtnIb.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseBtnIb.setOnClickListener{
            setPlayerStatus(true)
        }
        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
            binding.songTitleTv.text=intent.getStringExtra("title")
            binding.songSingerTv.text=intent.getStringExtra("singer")
        }
    }
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPlayBtnIb.visibility = View.VISIBLE
            binding.songPauseBtnIb.visibility = View.INVISIBLE
        }
        else{
            binding.songPlayBtnIb.visibility = View.INVISIBLE
            binding.songPauseBtnIb.visibility = View.VISIBLE
        }
    }
}