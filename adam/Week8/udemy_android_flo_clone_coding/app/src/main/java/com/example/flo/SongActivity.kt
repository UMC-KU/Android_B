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

//    var mediaPlayer:MediaPlayer?=null
//    var vol = 0.5f

    //전역 변수
    lateinit var binding : ActivitySongBinding
//    lateinit var song : Song
    lateinit var timer : Timer
    private var mediaPlayer: MediaPlayer? = null //raw에 들어가는 음악파일은 소문자로만 이뤄져야함!
    private var gson:Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initLayout()

        initPlayList()
        initSong()
        initClickListener()
//        setPlayer(song)


//        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
//            binding.songTitleTv.text = intent.getStringExtra("title")
//            binding.songSingerTv.text = intent.getStringExtra("singer")
//        }

    }


    override fun onPause() { //사용자가 포커스를 잃었을 때 음악이 중지
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        //일시 정지되면 그전까지 플레이 했던 플레이타임을 기억
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        //앱이 종료되어도 데이터 정보가 저장(내부 저장소에 데이터 저장)(간단한 값들을 저장)
        val editor = sharedPreferences.edit()
        //데이터(객체)를 다른 곳으로 보낼 때 제이슨 포맷으로 데이터를 보냄(중요!)
        //하나하나 보내야되는 번거로움을 줄여줌

//        val songJson = gson.toJson(songs[nowPos])
        editor.putInt("songId", songs[nowPos].id)

        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

    private fun initPlayList(){ //해당 DB에 저장되어 있는 정보를 뽑아와서 song에 저장해준다.
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songDownarrowIv.setOnClickListener {
            finish()
        }
        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songBtnSkipNextIv.setOnClickListener {
            moveSong(+1)
        } //다음노래 클릭했을 때

        binding.songBtnSkipPreIv.setOnClickListener {
            moveSong(-1)
        } //이전노래 클릭했을 때때

        binding.songMyLikeOffIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        } //좋아요 버튼 클릭했을 때때



    }

    private fun initSong(){
//        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second", 0),
//                intent.getIntExtra("playTime", 0),
//                intent.getBooleanExtra("isPlaying", false),
//                intent.getStringExtra("music")!!
//            )
//        }


        //sharedPreference에서 id값을 받아온 다음 songid를 통해 songs와 비교해서 index값을 구한다.
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id) //DB에도 좋아요정보 업데이트

        if(!isLike){
            binding.songMyLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songMyLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }


    private fun moveSong(direct: Int){
        if(nowPos + direct <0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        } //처음 노래일 때

        if(nowPos + direct >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        } //마지막 노래일 때

        nowPos += direct
        timer.interrupt() //원래 진행하던 타이머 쓰레드를 멈춘다
        startTimer() //재실행

        mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
//        binding.songTitleTv.text = intent.getStringExtra("title")!!
        binding.songTitleTv.text = song.title
//        binding.songSingerTv.text = intent.getStringExtra("singer")!!
        binding.songSingerTv.text = song.singer
        binding.songStarttimeTv.text = String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songFinishtimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isLike){
            binding.songMyLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songMyLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
        //좋아요 버튼에 대한 화면 구현

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.songBtnPlayIv.visibility = View.INVISIBLE
            binding.songBtnPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.INVISIBLE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){
        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try{
                while (true){
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
                                binding.songStarttimeTv.text = String.format("%02d:%02d", second/60, second%60)
                            }
                            second++
                        }

                    }
                }

            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")

            }

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




