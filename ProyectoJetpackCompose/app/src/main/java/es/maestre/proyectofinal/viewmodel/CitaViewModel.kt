package es.maestre.proyectofinal.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import es.maestre.proyectofinal.conexion.AppDatabase
import es.maestre.proyectofinal.conexion.CitaRepository
import es.maestre.proyectofinal.model.Cita
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de gestión de citas individuales y reservas.
 * Implementa el patrón MVVM, no SupaBase.
 */
class CitaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository

    // Horas disponibles para el centro VitalFit
    val horasDisponibles = listOf(
        "08:00", "08:45", "09:30", "10:15", "11:00", "11:45", "12:30", "13:15", "14:00"
    )

    init {
        val citaDAO = AppDatabase.getDatabase(application).citaDAO()
        repository = CitaRepository(citaDAO)
        Log.d("VitalFit_Debug", "CitaViewModel inicializado")
    }

    /**
     * Obtiene las citas de una fecha concreta.
     */
    fun getCitasPorFecha(fecha: String): LiveData<List<Cita>> {
        Log.i("VitalFit_Debug", "Consultando citas para: $fecha")
        return repository.getCitasByFecha(fecha)
    }

    /**
     * Actualiza el estado de una cita.
     */
    fun update(cita: Cita) = viewModelScope.launch {
        repository.update(cita)
        Log.d("VitalFit_Debug", "Cita en hora ${cita.hora} actualizada con éxito")
    }

    /**
     * Inserta una nueva cita en la base de datos.
     */
    fun insert(cita: Cita) = viewModelScope.launch {
        repository.insert(cita)
        Log.d("VitalFit_Debug", "Nueva cita insertada")
    }
}
