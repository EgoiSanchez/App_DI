package com.example.albumeter.BBDD

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// para la gestion de base de datos, las querys
@Dao
interface AlbumDAO {

    // con flow para hacerla reactiva a cambios y se actualize automaticamente
    @Query("SELECT * FROM Albumes")
    fun mostrarAlbumes(): Flow<List<Album>>

    @Insert
    suspend fun insertarAlbum(miAlbum: Album)

    // para tratar el disco de cada recycler view y no la posicion de los recycler
    @Query("SELECT * FROM Albumes where id like :id")
    fun buscarPorId(id:Int): Flow<Album>

    // como tengo el id de estar en detalles de disco se me hace mas facil usar una query en vez del metodo DELETE
    @Query ("DELETE FROM Albumes WHERE id = :id")
    suspend fun borrar(id: Int)

    //suspned para hacer con una courutine
    @Update
    suspend fun modificar(disco: Album)

}