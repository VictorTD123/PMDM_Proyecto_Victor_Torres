package es.maestre.proyectofinal.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.maestre.proyectofinal.conexion.AppDatabase
import es.maestre.proyectofinal.conexion.CitaRepository
import es.maestre.proyectofinal.model.Cita

/**
 * ViewModel para la pantalla de "Mis Citas".
 * Utiliza LiveData para observar cambios en la base de datos.
 */
class MisCitasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository
    
    // LiveData que contiene la lista de todas las citas. 
    // Al ser LiveData, la UI se actualizará automáticamente gracias a observeAsState en Compose.
    val todasLasCitas: LiveData<List<Cita>>

    init {
        Log.d("VitalFit_Debug", "Inicializando MisCitasViewModel")
        
        val citaDAO = AppDatabase.getDatabase(application).citaDAO()
        repository = CitaRepository(citaDAO)
        
        // Obtenemos todas las citas desde el repositorio
        todasLasCitas = repository.getAllCitas()
        
        Log.i("VitalFit_Debug", "Cargando flujo de citas desde la base de datos")
    }
}
