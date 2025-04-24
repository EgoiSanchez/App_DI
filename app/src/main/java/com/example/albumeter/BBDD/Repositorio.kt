package com.example.albumeter.BBDD

import kotlinx.coroutines.flow.Flow

class Repositorio(val miDAO: AlbumDAO) {
    fun mostrarAlbumes(): Flow<List<Album>> {
        return miDAO.mostrarAlbumes()
    }
}