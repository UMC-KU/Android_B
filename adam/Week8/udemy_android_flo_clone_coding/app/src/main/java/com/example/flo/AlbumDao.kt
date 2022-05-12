package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album : Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    @Insert
    fun likeAlbum(like : Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId:Int, albumId:Int) : Int?
    //좋아요 눌렀는지 안눌렀는지 확인

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int)
    //좋아요 취소했는지 확인

    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbums(userId: Int) : List<Album>
    //보관함에서 유저를 구분해서 좋아요한 앨범에 관한 정보를 가져오는
    //LikeTable as LT : 해당 query문에서 LikeTable을 LT로 대신 사용하겠다.(너무 길어서 쓰는 듯)
    //JOIN..?
}