package com.example.albumeter.Modelo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.albumeter.BBDD.Album
import com.example.albumeter.BBDD.Repositorio
import kotlinx.coroutines.launch

class VM(val miRepositorio: Repositorio): ViewModel() {

    var usuario: Usuario?=null

    //usando copilot, convierte el flow en livedata para poder iniciarlo desde ya
    val listaAlbumes: LiveData<List<Album>> = miRepositorio.mostrarAlbumes().asLiveData()
    lateinit var album: LiveData<Album>

    //meto todas las funciones de las base de datos qur quiera usar
   // fun mostrarAlbum() = viewModelScope.launch {
       // miRepositorio.mostrarAlbumes().collect { lista ->
         //   listaAlbumes = lista.asLiveData()
       // }
    //}

    fun buscarAlbumPorId(id: Int): LiveData<Album> {
        return miRepositorio.buscarPorId(id).asLiveData()
    }


    fun agregarDisco(miDisco: Album) {
        viewModelScope.launch {
            Log.d("Depuración", "Intentando insertar disco: ${miDisco.titulo}")
            miRepositorio.insertar(miDisco)
            Log.d("Depuración", "Disco insertado correctamente") }

    }


}

// para que solo se instancie una vez
class AlbumViewModelFactory(private val miRepositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}