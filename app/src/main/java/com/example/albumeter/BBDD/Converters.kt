package com.example.albumeter.BBDD

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.sql.Date

// implemento la clase por que Room no soporta java.sql.Date
class Converters {

    //se convierte la fecha a un Long(timestamp)
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // recibe un Date que puede ser null y devuelve un long que puede ser null
    //si date no es null devuelve .time que son milisegundos desde 1 enero de 1970
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    //se guarda el estado como un string en vez de enum
    @TypeConverter
    fun fromEstado(value: Estado?): String? {
        return value?.name
    }

    @TypeConverter
    fun toEstado(value: String?): Estado? {
        return value?.let { Estado.valueOf(it) }
    }

    // Se pasa la lista a una cadena de strings separados por una coma
    @TypeConverter
    fun fromListString(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toListString(value: String?): List<String>? {
        return value?.split(",")?.filter { it.isNotEmpty() }
    }


}