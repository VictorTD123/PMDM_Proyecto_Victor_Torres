package es.maestre.proyectofinal.conexion

import androidx.lifecycle.LiveData
import es.maestre.proyectofinal.model.Cita

class CitaRepository(private val citaDAO: CitaDAO) {

    fun getCitasByFecha(fecha: String): LiveData<List<Cita>> {
        return citaDAO.getCitasByFecha(fecha)
    }

    // Nueva función para obtener todas las citas
    fun getAllCitas(): LiveData<List<Cita>> {
        return citaDAO.getAllCitas()
    }

    suspend fun insert(cita: Cita) {
        citaDAO.insert(cita)
    }

    suspend fun update(cita: Cita) {
        citaDAO.update(cita)
    }
}
