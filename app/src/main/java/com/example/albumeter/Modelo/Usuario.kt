package com.example.albumeter.Modelo

import com.example.albumeter.BBDD.Album

class Usuario (var nombre:String, var apellido:String, var edad:Int){
    var lista: MutableList<Album> = mutableListOf()
}