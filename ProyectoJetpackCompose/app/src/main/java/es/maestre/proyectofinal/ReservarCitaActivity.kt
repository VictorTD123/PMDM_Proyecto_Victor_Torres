package es.maestre.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.maestre.proyectofinal.model.Cita
import es.maestre.proyectofinal.ui.theme.HoraLibre
import es.maestre.proyectofinal.ui.theme.HoraOcupada
import es.maestre.proyectofinal.ui.theme.ProyectoFinalTheme
import es.maestre.proyectofinal.viewmodel.CitaViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla para reservar una nueva cita.
 */
class ReservarCitaActivity : ComponentActivity() {
    

    private val viewModel: CitaViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalTheme {
                var showDatePicker by remember { mutableStateOf(false) }
                
                // rememberDatePickerState permite ofrece la funcionalidad de calendario
                val datePickerState = rememberDatePickerState(
                    selectableDates = object : SelectableDates {
                        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                            // No permitimos reservar en el pasado
                            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                            calendar.set(Calendar.HOUR_OF_DAY, 0)
                            calendar.set(Calendar.MINUTE, 0)
                            calendar.set(Calendar.SECOND, 0)
                            calendar.set(Calendar.MILLISECOND, 0)
                            return utcTimeMillis >= calendar.timeInMillis
                        }
                    }
                )
                
                var fechaSeleccionada by remember { mutableStateOf(obtenerFechaActual()) }
                
                // Observamos las citas filtradas por fecha desde el ViewModel (Capa de datos)
                val citasDb by viewModel.getCitasPorFecha(fechaSeleccionada).observeAsState(initial = emptyList())

                val citasParaMostrar = remember(citasDb, fechaSeleccionada) {
                    val hoy = obtenerFechaActual()
                    val horaActual = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    
                    val base = if (citasDb.isEmpty()) {
                        viewModel.horasDisponibles.map { hora ->
                            Cita(fecha = fechaSeleccionada, hora = hora, reservada = false)
                        }
                    } else {
                        citasDb
                    }

                    if (fechaSeleccionada == hoy) {
                        base.filter { it.hora > horaActual }
                    } else {
                        base
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.menu_item_book_appointment)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        )
                    }
                ) { padding ->
                    Column(modifier = Modifier.padding(paddingValues = padding).padding(all = 16.dp)) {
                        Text(text = stringResource(id = R.string.title_choose_day), style = MaterialTheme.typography.titleLarge)
                        
                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null)
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            Text(text = "Fecha: $fechaSeleccionada")
                        }

                        if (showDatePicker) {
                            DatePickerDialog(
                                onDismissRequest = { showDatePicker = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        val selectedDate = datePickerState.selectedDateMillis
                                        if (selectedDate != null) {
                                            fechaSeleccionada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedDate))
                                        }
                                        showDatePicker = false
                                    }) { Text(text = "OK") }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }

                        Spacer(modifier = Modifier.height(height = 16.dp))
                        Text(text = stringResource(id = R.string.label_available_appointments), style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(height = 16.dp))

                        if (citasParaMostrar.isEmpty()) {
                            Text(text = "No hay más citas disponibles para hoy.", color = Color.Gray)
                        } else {
                            // LazyVerticalGrid: Componente que permite mostrar una lista de elementos en una cuadrícula
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(count = 3),
                                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                            ) {
                                items(items = citasParaMostrar) { cita ->
                                    // Pasamos datos y lambdas a la función componible sin estado
                                    HoraItem(cita = cita) {
                                        if (!cita.reservada) {
                                            if (cita.idCita == 0) {
                                                viewModel.insert(cita.copy(reservada = true))
                                            } else {
                                                viewModel.update(cita.copy(reservada = true))
                                            }
                                            Toast.makeText(this@ReservarCitaActivity, "¡Cita reservada!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@ReservarCitaActivity, "Esta hora ya está ocupada", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun obtenerFechaActual(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
}


@Composable
fun HoraItem(cita: Cita, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (cita.reservada) HoraOcupada else HoraLibre,
            contentColor = Color.Black
        ),
        modifier = Modifier.height(height = 60.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = cita.hora)
    }
}
