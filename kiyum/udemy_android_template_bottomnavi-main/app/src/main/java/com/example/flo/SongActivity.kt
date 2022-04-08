package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songPannelBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songPannelPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPannelPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPannelRepeatIv.setOnClickListener{
            setRepeatStatus(true)
        }
        binding.songPannelRepeatActiveIv.setOnClickListener {
            setRepeatStatus(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false),
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songPannelTitleTv.text=intent.getStringExtra("title")!!
        binding.songPannelSingerTv.text=intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }


    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPannelPlayIv.visibility = View.GONE
            binding.songPannelPauseIv.visibility = View.VISIBLE
        }
        else{
            binding.songPannelPlayIv.visibility = View.VISIBLE
            binding.songPannelPauseIv.visibility = View.GONE
        }
    }
    fun setRepeatStatus(isActive: Boolean){
        if(isActive){
            binding.songPannelRepeatIv.visibility = View.GONE
            binding.songPannelRepeatActiveIv.visibility = View.VISIBLE
        }
        else{
            binding.songPannelRepeatIv.visibility = View.VISIBLE
            binding.songPannelRepeatActiveIv.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run(){
            super.run()
            try{
                while(true){
                    if(second >= playTime){
                        break
                    }
                    if(isPlaying){
                        sleep(50)
                        mills += 50
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("song","쓰레드가 죽었습니다.${e.message}")
            }


        }
    }
}