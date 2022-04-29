package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songPlayBtnIv.setOnClickListener {
            setPlayerStatus(true)

        }

        binding.songPauseBtnIv.setOnClickListener {
            setPlayerStatus(false)

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

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songMusicProgressSb.progress * song.playTime) / 100) / 1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()



    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // song data class 초기화, 타이머 시작
    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }

        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songMusicSingerTv.text = intent.getStringExtra("singer")!!
        binding.songMusicCurrentTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songMusicEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songMusicProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        setPlayerStatus(song.isPlaying)
    }


    private fun setSongUnLike(unLike: Boolean) {
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

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songPlayBtnIv.visibility = View.GONE
            binding.songPauseBtnIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songPlayBtnIv.visibility = View.VISIBLE
            binding.songPauseBtnIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }


    private fun setSongLike(isLike: Boolean) {
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

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }


    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

        private var second: Int = 0
        private var milliSecond: Float = 0.0f

        override fun run() {
            super.run()

            // interrupt 발생 시 로그 띄우기
            try {
                while (true) {

                    if (second >= playTime) {
                        break
                    }

                    //seekbar progress 적용하기
                    if (isPlaying) {
                        sleep(50)
                        milliSecond += 50
                        runOnUiThread {
                            binding.songMusicProgressSb.progress =
                                ((milliSecond / playTime) * 100).toInt()
                        }

                        //1초가 지나면 second에 1 더하기
                        if (milliSecond % 1000 == 0f) {
                            runOnUiThread {
                                binding.songMusicCurrentTimeTv.text =
                                    String.format("%02d:%02d", second / 60, second % 60)

                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }
}