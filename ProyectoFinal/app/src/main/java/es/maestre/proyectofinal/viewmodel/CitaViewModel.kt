package es.maestre.proyectofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import es.maestre.proyectofinal.conexion.AppDatabase
import es.maestre.proyectofinal.conexion.CitaRepository
import es.maestre.proyectofinal.model.Cita
import kotlinx.coroutines.launch

class CitaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository

    init {
        val citaDAO = AppDatabase.getDatabase(application).citaDAO()
        repository = CitaRepository(citaDAO)
    }

    fun getCitasPorFecha(fecha: String): LiveData<List<Cita>> {
        return repository.getCitasByFecha(fecha)
    }

    fun update(cita: Cita) = viewModelScope.launch {
        repository.update(cita)
    }

    fun insert(cita: Cita) = viewModelScope.launch {
        repository.insert(cita)
    }
}
