package es.maestre.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.maestre.proyectofinal.ui.theme.ProyectoFinalTheme

/**
 * Pantalla informativa de VitalFit.
 * Utilizo scroll y otros componentes de Material 3.
 */
class AcercaDeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        setContent {
            ProyectoFinalTheme {
                Scaffold(
                    topBar = {
                        // TopAppBar: Componente de la UI que describe el título y navegación
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.acerca_de_toolbar_title)) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        )
                    }
                ) { padding ->
                    AcercaDeContent(modifier = Modifier.padding(paddingValues = padding))
                }
            }
        }
    }
}

/**
 * Función que describe el contenido de la pantalla Acerca De.
 */
@Composable
fun AcercaDeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            // rememberScrollState permite que Compose realice un seguimiento del estado del scroll(si fuese necesario hacer scroll)
            .verticalScroll(state = rememberScrollState())
    ) {
        // ElevatedCard: Componente que permite alterar la apariencia
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.acerca_de_card_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
                
                // HorizontalDivider: Componente que sirve para separar visualmente
                HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.secondary)
                
                Spacer(modifier = Modifier.height(height = 16.dp))
                
                Text(
                    text = stringResource(id = R.string.acerca_de_card_content),
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )
            }
        }
    }
}
