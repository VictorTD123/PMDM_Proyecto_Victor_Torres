package es.maestre.proyectofinal

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.maestre.proyectofinal.model.Cita
import es.maestre.proyectofinal.ui.theme.ProyectoFinalTheme
import es.maestre.proyectofinal.viewmodel.CitaViewModel

class DetalleCitaActivity : ComponentActivity() {
    
    // Obtenemos el ViewModel para poder actualizar la base de datos
    private val viewModel: CitaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val cita = intent.getSerializableExtra("CITA_SELECCIONADA") as? Cita

        setContent {
            ProyectoFinalTheme {
                Scaffold(
                    topBar = {
                        @OptIn(ExperimentalMaterial3Api::class)
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.label_detalles_cita)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        )
                    }
                ) { padding ->
                    cita?.let { citaSeleccionada ->
                        DetalleCitaContent(
                            cita = citaSeleccionada, 
                            modifier = Modifier.padding(paddingValues = padding),
                            onCancelarClick = {
                                // Lógica para cancelar la cita y actualizamos el estado de la cita en la base de datos
                                val citaActualizada = citaSeleccionada.copy(reservada = false)
                                viewModel.update(citaActualizada)
                                
                                Log.d("VitalFit_Debug", "Cita cancelada: ${citaActualizada.fecha} ${citaActualizada.hora}")
                                Toast.makeText(this, "Cita cancelada con éxito", Toast.LENGTH_SHORT).show()
                                
                                // Cerramos la pantalla para volver a la lista
                                finish()
                            }
                        )
                    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No se ha seleccionado ninguna cita")
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleCitaContent(
    cita: Cita, 
    modifier: Modifier = Modifier,
    onCancelarClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_fecha_cita)) },
            supportingContent = { Text(text = cita.fecha, style = MaterialTheme.typography.headlineSmall) },
            leadingContent = { Icon(imageVector = Icons.Default.Event, contentDescription = null) }
        )
        
        HorizontalDivider()

        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_hora_cita)) },
            supportingContent = { Text(text = cita.hora, style = MaterialTheme.typography.headlineSmall) },
            leadingContent = { Icon(imageVector = Icons.Default.Schedule, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(height = 32.dp))

        // Botón funcional que ejecuta la lambda de cancelación
        Button(
            onClick = onCancelarClick, 
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Cancelar Cita")
        }
    }
}
