package com.example.albumeter.BBDD

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio(val miDAO: AlbumDAO) {
    fun mostrarAlbumes(): Flow<List<Album>> {
        return miDAO.mostrarAlbumes()
    }

    @WorkerThread
    suspend fun insertar(miDisco: Album){
        miDAO.insertarAlbum(miDisco)
    }
}