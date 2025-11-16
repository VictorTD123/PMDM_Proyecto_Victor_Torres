package es.maestre.proyectofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import es.maestre.proyectofinal.conexion.AppDatabase
import es.maestre.proyectofinal.conexion.CitaRepository
import es.maestre.proyectofinal.model.Cita
import es.maestre.proyectofinal.model.EstadoHora
import kotlinx.coroutines.launch

class ReservarCitaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository

    private val _estadoHoras = MediatorLiveData<List<EstadoHora>>()
    val estadoHoras: LiveData<List<EstadoHora>> = _estadoHoras

    // Lista fija de todas las horas de trabajo posibles.
    private val todasLasHoras = listOf(
        "08:00", "08:45", "09:30",
        "10:15", "11:00", "11:45",
        "12:30", "13:15", "14:00"
    )

    init {
        val citaDAO = AppDatabase.getDatabase(application).citaDAO()
        repository = CitaRepository(citaDAO)
    }

    //Recibe una fecha, consulta la BD y calcula el estado de cada hora.

    fun cargarEstadoHoras(fecha: String) {
        val citasDelDiaLiveData = repository.getCitasByFecha(fecha)

        _estadoHoras.addSource(citasDelDiaLiveData) { citasOcupadas ->
            // Cada vez que las citas en la BD cambian, este código se ejecuta.
            val horasOcupadas = citasOcupadas.map { it.hora }

            val listaEstadoHoras = todasLasHoras.map { hora ->
                EstadoHora(hora = hora, estaLibre = !horasOcupadas.contains(hora))
            }
            
            _estadoHoras.value = listaEstadoHoras
        }
    }

    // Inserta una nueva cita en la base de datos

    fun insertarCita(cita: Cita) = viewModelScope.launch {
        repository.insert(cita)
    }
}