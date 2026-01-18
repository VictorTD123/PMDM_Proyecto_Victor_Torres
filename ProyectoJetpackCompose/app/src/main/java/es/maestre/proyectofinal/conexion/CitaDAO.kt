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

    // Filtra solo las citas que están reservadas para que no aparezcan las canceladas
    @Query("SELECT * FROM cita WHERE reservada = 1 ORDER BY fecha ASC, hora ASC")
    fun getAllCitas(): LiveData<List<Cita>>

    @Update
    suspend fun update(cita: Cita)

}
