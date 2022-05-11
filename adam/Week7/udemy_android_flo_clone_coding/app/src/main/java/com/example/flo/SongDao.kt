package com.example.flo

import androidx.room.*


@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<Song>
    //select : 데이터 선택해서 가져와라, * : 모든 데이터

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike = :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean, id: Int)
    //DB에도 좋아요 정보 저장하기 위한 작업
    //SongTable에 매개변수 값으로 넘겨준 id의 데이터의 islike정보를 바꿀것이다.

    @Query("SELECT * FROM SongTable WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>

    //songDB에서 islike값이 true인 값들만 가져오는 것
}