package es.maestre.proyectofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.maestre.proyectofinal.model.ConsejoSalud
import es.maestre.proyectofinal.network.Ktorclient
import es.maestre.proyectofinal.network.RepositorioSalud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SaludViewModel(application: Application) : AndroidViewModel(application) {
    
    // Inicializamos con un consejo de bienvenida para que la pantalla no esté vacía,ya que si no hago esto me da error al mostrar la api
    val consejos = MutableStateFlow<List<ConsejoSalud>>(
        listOf(ConsejoSalud(0, "Cuidar tu salud es la mejor inversión. ¡Bienvenido a VitalFit!"))
    )
    
    private val repository: RepositorioSalud = RepositorioSalud(Ktorclient.httpclient)

    init {
        loadConsejos()
    }

    fun loadConsejos() {
        viewModelScope.launch {
            try {
                val respuesta = repository.getConsejo()
                // Al recibir la respuesta de la API, sustituimos el mensaje de bienvenida
                consejos.value = listOf(respuesta.slip)
            } catch (e: Exception) {
                e.printStackTrace()
                // Si hay error de internet, mantenemos un mensaje para que se vea algo
                consejos.value = listOf(ConsejoSalud(0, "La constancia es la clave del éxito. ¡Sigue adelante!"))
            }
        }
    }
}
