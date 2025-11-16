package es.maestre.proyectofinal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cita")
data class Cita(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_cita") var idCita: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: String,
    @ColumnInfo(name = "hora") var hora: String,
    @ColumnInfo(name = "reservada") var reservada: Boolean = false
) : Serializable
