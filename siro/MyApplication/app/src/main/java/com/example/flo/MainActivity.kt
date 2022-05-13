package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    lateinit var timer : SongActivity.Timer
    private var song:Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()

        //val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false, "music_bboom")

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
           // startActivity(Intent(this,SongActivity::class.java))
//            val intent = Intent(this,SongActivity::class.java)
//            intent.putExtra("title",song.title)
//            intent.putExtra("singer",song.singer)
//            intent.putExtra("second",song.second)
//            intent.putExtra("playTime",song.playTime)
//            intent.putExtra("isPlaying",song.isPlaying)
//            intent.putExtra("music",song.music)
//            startActivity(intent)
        }
        initBottomNavigation()

       //startTimer()

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.mainMiniplayerPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }


        Log.d("Song",song.title + song.singer)
    }

//    private fun startTimer(){
//        timer = SongActivity.Timer(song.playTime, song.isPlaying)
//        timer.start()
//    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song:Song){
        binding.mainMiniplayerTitleTv.text=song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainSb.progress=(song.second*100000)/song.playTime
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying=isPlaying
        //timer.isPlaying=isPlaying

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainMiniplayerPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainMiniplayerPauseBtn.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData",null)
//
//        song = if(songJson == null){
//            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
//        }else{
//            gson.fromJson(songJson,Song::class.java)
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.songDao().getSong(1)
        }else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                240,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                230,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_boy",
                R.drawable.img_album_exp4,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                230,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(0,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )
        songDB.albumDao().insert(
            Album(
                1,
                "Lilac", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3
            )
        )
        songDB.albumDao().insert(
            Album(
                3, "Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4
            )
        )
        songDB.albumDao().insert(
            Album(
                4,
                "BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
}