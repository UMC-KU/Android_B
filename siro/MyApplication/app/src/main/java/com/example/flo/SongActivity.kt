package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
   // lateinit var song :Song
    lateinit var timer : Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

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
        //setPlayer(song)

//        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
//            binding.songTitleTv.text=intent.getStringExtra("title")!!
//            binding.songSingerTv.text=intent.getStringExtra("singer")!!
//        }
    }

    //사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying=false
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()   //에디터

        //val songJson = gson.toJson(songs[nowPos])
        //editor.putString("songData",songJson)
        editor.putInt("songId",songs[nowPos].id)    //song의 id 값

        editor.apply()

    }
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()  //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어플레이어 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songBtnDownIb.setOnClickListener{
            finish()
        }
        //binding.songProgressSb.setOnSeekBarChangeListener()
        binding.songPlayBtnIb.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseBtnIb.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songSkipNextIb.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIb.setOnClickListener {
            moveSong(-1)
        }
        binding.songMyLikeIb.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }
    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())
//        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
//            song=Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second",0),
//                intent.getIntExtra("playTime",0),
//                intent.getBooleanExtra("isPlaying",false),
//                intent.getStringExtra("music")!!
//            )
//        }
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike:Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songMyLikeIb.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songMyLikeIb.setImageResource((R.drawable.ic_my_like_off))
        }
    }

    private fun moveSong(direct:Int){
        if(nowPos+direct<0 ){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos+direct>=songs.size){
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
            return
        }
        nowPos+=direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId:Int):Int{
        for(i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song:Song){
//        binding.songTitleTv.text=intent.getStringExtra("title")!!
//        binding.songSingerTv.text=intent.getStringExtra("singer")!!

        binding.songTitleTv.text=song.title
        binding.songSingerTv.text=song.singer
        binding.songStartSecondTv.text = String.format("%02d:%02d",song.second / 60,song.second % 60)
        binding.songEndSecondTv.text = String.format("%02d:%02d",song.playTime / 60,song.second % 60)
        binding.songAlbumImgIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second*1000 / song.playTime)
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)

        if(song.isLike){
            binding.songMyLikeIb.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songMyLikeIb.setImageResource((R.drawable.ic_my_like_off))
        }
        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying=isPlaying
        timer.isPlaying=isPlaying

        if(isPlaying){
            binding.songPlayBtnIb.visibility = View.INVISIBLE
            binding.songPauseBtnIb.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songPlayBtnIb.visibility = View.VISIBLE
            binding.songPauseBtnIb.visibility = View.INVISIBLE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime : Int,var isPlaying:Boolean=true):Thread(){
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

                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if (mills%1000==0f){
                            runOnUiThread{
                                binding.songStartSecondTv.text = String.format("%02d:%02d",second / 60,second % 60)
                            }
                            second++
                        }

                    }
                }
            }catch(e:InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
}