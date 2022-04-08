package com.example.flo

//제목, 가수, 재생 시간, 현재 재생 시간, 재생중인지
data class Song(
    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false
)
