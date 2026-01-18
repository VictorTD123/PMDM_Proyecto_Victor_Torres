package es.maestre.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import es.maestre.proyectofinal.data.DataStoreManager
import es.maestre.proyectofinal.ui.theme.ProyectoFinalTheme
import es.maestre.proyectofinal.viewmodel.SaludViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager
    private val saludViewModel: SaludViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)
        
        setContent {
            ProyectoFinalTheme {
                MainScreen(
                    saludViewModel = saludViewModel,
                    onLoginClick = {
                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                    },
                    onLanguageChange = { lang ->
                        changeLanguage(lang)
                    }
                )
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        //verificamos que el cambio de idioma se ha hecho correctamente
        Log.d("VitalFit_Debug", "Cambiando idioma a: $languageCode")
        
        lifecycleScope.launch {
            dataStoreManager.saveLanguage(languageCode)
        }
        
        // Aplicamos el cambio de idioma en tiempo real
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    saludViewModel: SaludViewModel,
    onLoginClick: () -> Unit, 
    onLanguageChange: (String) -> Unit
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    
    val listaConsejos by saludViewModel.consejos.collectAsState()

    LaunchedEffect(Unit) {
        saludViewModel.loadConsejos()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.Language, contentDescription = "Idioma")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text(text = "Español") },
                            onClick = { 
                                onLanguageChange("es")
                                showMenu = false 
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "English") },
                            onClick = { 
                                onLanguageChange("en")
                                showMenu = false 
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_vitalfit_foreground),
                contentDescription = "Logo VitalFit",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "VitalFit",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (listaConsejos.isNotEmpty()) {
                val consejoHoy = listaConsejos.random()
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Consejo de Salud:", style = MaterialTheme.typography.labelLarge)
                        Text(
                            text = consejoHoy.consejo, 
                            style = MaterialTheme.typography.bodyMedium, 
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de usuario y contraseña deshabilitados para que no lo seleccionen
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(R.string.hint_username)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(R.string.hint_password)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.button_start))
            }
            
            TextButton(onClick = { 
                Toast.makeText(context, context.getString(R.string.toast_feature_pending), Toast.LENGTH_SHORT).show()
            }) {
                Text(text = stringResource(R.string.Introduccion))
            }
        }
    }
}
