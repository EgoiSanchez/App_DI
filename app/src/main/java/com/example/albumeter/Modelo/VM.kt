package com.example.albumeter.Modelo

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
    lateinit var listaAlbumes: LiveData<List<Album>>

    //meto todas las funciones de las base de datos qur quiera usar
    fun mostrarAlbum() = viewModelScope.launch {
        listaAlbumes = miRepositorio.mostrarAlbumes().asLiveData()
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