package com.example.albumeter.BBDD

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "Albumes")

data class Album(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @NonNull var banda: String,
    @NonNull var titulo: String,
    var estilo: String? = null,
    var ano: Int? = null,
    var fecha : Date? = null,
    var pais : String? =null,
    var nota : Double? = null,
    var estado : Estado = Estado.ALBUM_ANADIDO,
    var portada : String? = null,
    var descripcion : String? = null,
    var tags : List<String> = emptyList()

) {}

enum class Estado {

    ALBUM_ANADIDO,
    ESCUCHADO,
    PENDIENTE_CALIFICACION,
    CALIFICADO,

}