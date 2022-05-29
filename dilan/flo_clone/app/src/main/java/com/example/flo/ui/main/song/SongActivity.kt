package com.example.flo.ui.main.song

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.data.entities.Song
import com.example.flo.data.local.SongDatabase
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()

    }

    override fun onPause() {
        super.onPause()

        songs[nowPos].second = ((binding.songMusicProgressSb.progress * songs[nowPos].playTime) / 100) / 1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initPlayList() {
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener() {

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songPlayBtnIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseBtnIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNextBtnIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songPreviousBtnIv.setOnClickListener {
            moveSong(-1)

        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }

    }

    private fun setLike(isLike: Boolean) {
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }


    }

    // song data class 초기화, 타이머 시작
    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song Id", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "First song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "Last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct
        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])

    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songMusicSingerTv.text = song.singer
        binding.songMusicCurrentTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songMusicEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumArtIv.setImageResource(song.coverImg!!)
        binding.songMusicProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
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



    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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