package es.maestre.proyectofinal.conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import es.maestre.proyectofinal.model.Cita

@Dao
interface CitaDAO {

    @Insert
    suspend fun insert(cita: Cita)

    @Query("SELECT * FROM cita WHERE fecha = :fecha ORDER BY hora ASC")
    fun getCitasByFecha(fecha: String): LiveData<List<Cita>>

    // Nueva función para obtener todas las citas, ordenada por fecha y hora
    @Query("SELECT * FROM cita ORDER BY fecha ASC, hora ASC")
    fun getAllCitas(): LiveData<List<Cita>>

    @Update
    suspend fun update(cita: Cita)

}
