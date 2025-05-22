package com.example.albumeter.BBDD

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDAO {

    @Query("SELECT * FROM Albumes")
    fun mostrarAlbumes(): Flow<List<Album>>

    @Insert
    suspend fun insertarAlbum(miAlbum: Album)

    @Query("SELECT * FROM Albumes where id like :id")
    fun buscarPorId(id:Int): Flow<Album>
}