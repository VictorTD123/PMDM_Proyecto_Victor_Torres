package es.maestre.proyectofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.maestre.proyectofinal.conexion.AppDatabase
import es.maestre.proyectofinal.conexion.CitaRepository
import es.maestre.proyectofinal.model.Cita

// Siguiendo el patrón de tu AbogadoViewModel
class MisCitasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository
    // Este LiveData contendrá la lista de todas las citas del usuario
    val todasLasCitas: LiveData<List<Cita>>

    init {
        val citaDAO = AppDatabase.getDatabase(application).citaDAO()
        repository = CitaRepository(citaDAO)
        // Al inicializarse, pide al repositorio todas las citas
        todasLasCitas = repository.getAllCitas() // Necesitaremos añadir esta función al Repo y al DAO
    }
}
