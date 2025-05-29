package com.example.albumeter.BBDD

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//recibo instancia de AlbumDAO para usarlo de conexion con la BBDD
class Repositorio(val miDAO: AlbumDAO) {
    fun mostrarAlbumes(): Flow<List<Album>> {
        return miDAO.mostrarAlbumes()
    }

    //@WorkerThread lo elimino por que lo estamos lanzando en otro hilo separado dentro del viewModelScoper.launch

    suspend fun insertar(miDisco: Album) {

        try {
            miDAO.insertarAlbum(miDisco)
            Log.d("Depuración", "Inserción en la base de datos exitosa.")
        } catch (e: Exception) {
            Log.e("Error BD", "Error insertando disco: ${e.message}")
        }
    }

    suspend fun borrarAlbum(id: Int) {
        try {
            miDAO.borrar(id)
            Log.d("Depuración", "Se ha borrado el disco correctamente.")
        } catch (e: Exception) {
            Log.e("Error BD", "Error borrando el disco: ${e.message}")
        }

    }

    suspend fun modificarAlbum(miDisco: Album) {

        try {
            miDAO.modificar(miDisco)
            Log.d("Depuración", "Se ha modificado el disco correctamente.")
        } catch (e: Exception) {
            Log.e("Error BD", "Error modificando el disco: ${e.message}")
        }

    }

    //@WorkerThread
    fun buscarPorId(id: Int): Flow<Album> {

        return miDAO.buscarPorId(id)
    }


}