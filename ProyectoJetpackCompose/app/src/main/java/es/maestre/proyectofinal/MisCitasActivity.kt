package es.maestre.proyectofinal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import es.maestre.proyectofinal.model.Cita
import es.maestre.proyectofinal.ui.theme.ProyectoFinalTheme
import es.maestre.proyectofinal.viewmodel.MisCitasViewModel

class MisCitasActivity : ComponentActivity() {
    private val viewModel: MisCitasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalTheme {
                val citas by viewModel.todasLasCitas.observeAsState(initial = emptyList())
                
                MisCitasScreen(
                    citas = citas,
                    onCitaClick = { cita ->
                        val intent = Intent(this, DetalleCitaActivity::class.java)
                        intent.putExtra("CITA_SELECCIONADA", cita)
                        startActivity(intent)
                    },
                    onNavigateHome = {
                        val intent = Intent(this, InicioActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    },
                    onLogout = {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(
    citas: List<Cita>,
    onCitaClick: (Cita) -> Unit,
    onNavigateHome: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.title_mis_citas)) })
        },
        bottomBar = {
            NavigationBar {
                // 1. SALIR (Izquierda)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Logout, null) },
                    label = { Text(text = "Salir") },
                    selected = false,
                    onClick = onLogout
                )
                // 2. INICIO (Centro)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text(text = stringResource(id = R.string.menu_home)) },
                    selected = false,
                    onClick = onNavigateHome
                )
                // 3. MIS CITAS (Derecha)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CalendarMonth, null) },
                    label = { Text(text = stringResource(id = R.string.menu_my_appointments)) },
                    selected = true,
                    onClick = { /* Estamos aquí */ }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            items(items = citas) { cita ->
                CitaItem(cita = cita, onClick = { onCitaClick(cita) })
            }
        }
    }
}

@Composable
fun CitaItem(cita: Cita, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = cita.fecha, fontWeight = FontWeight.Bold)
                Text(text = cita.hora, style = MaterialTheme.typography.bodyMedium)
            }
            SuggestionChip(
                onClick = {},
                label = { Text(text = "Reservada", color = Color.White) },
                colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    }
}
