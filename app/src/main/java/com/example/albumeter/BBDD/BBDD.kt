package com.example.albumeter.BBDD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date

@Database(entities = arrayOf(Album::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BBDD : RoomDatabase() {
    abstract fun miDAO(): AlbumDAO

    companion object {
        @Volatile
        private var INSTANCE: BBDD? = null
        fun getDatabase(context: Context): BBDD {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BBDD::class.java,
                    "my_app_database"
                )
                    //iniciar la base de datos con datos
                    .addCallback(roomCallback)
                    .build().also { INSTANCE = it }
            }
        }

        // segunda parte del callback
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)


                val discosIniciales = listOf(
                    Album(
                        banda = "Baroness",
                        titulo = "Purple",
                        estilo = "Sludge",
                        ano = 2015,
                        fecha = Date.valueOf("2015-12-18"),
                        pais = "Estados Unidos",
                        nota = 9.9,
                        estado = Estado.CALIFICADO,
                        portada = null,
                        descripcion = "Primer album añadido a la BBDD",
                        tags = listOf("Sludge", "Metal", "Rock")
                    ),


                    )
                // Inserta los albumes iniciales en la base de datos
                val viewModelScope = CoroutineScope(Dispatchers.IO)
                viewModelScope.launch {

                    discosIniciales.forEach { album ->
                        INSTANCE?.miDAO()?.insertarAlbum(album)
                    }
                }
            }
        }
    }


}