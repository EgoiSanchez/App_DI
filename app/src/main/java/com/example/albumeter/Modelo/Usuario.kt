package com.example.albumeter.Modelo

import com.example.albumeter.BBDD.Album

class Usuario (var nombre:String, var correo:String, var password:String){
    var lista: MutableList<Album> = mutableListOf()
}