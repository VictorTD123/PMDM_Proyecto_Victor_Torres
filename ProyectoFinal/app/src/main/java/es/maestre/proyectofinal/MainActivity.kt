package es.maestre.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import es.maestre.proyectofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Esta es la función principal que se llama cuando se crea la pantalla.
    // Es donde creamos el diseño ("activity_main.xml") usando ViewBinding para poder
    // acceder a los botones y vistas, y lo conectamos a la pantalla con el setContentView,
    // y configuramos la barra superior (Toolbar) para que funcione.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí le decimos a la pantalla que nuestra barra de herramientas 'toolbarLanguage' va a ser la barra de acción principal.
        // Inmediatamente después, le quitamos el título, porque solo queremos que muestre el menú de los tres puntitos.
        setSupportActionBar(binding.toolbarIdioma)
        supportActionBar?.title = "IDIOMA/LANGUAGE"

        // Este es el listener para el botón principal de "Comenzamos".
        // Cuando el usuario lo pulsa, crea un Intent para abrir la pantalla
        // de InicioActivity y la lanza, llevando al usuario a la parte principal de la app.
        binding.btnUnirPantalla.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

        // Estos son unos listener que son invisibles y esta encima de los textImput, para que salte el toast.
        // Y no darle opcion a escribir todavia, ya que el login lo implementare cuando tenga los recursos para hacerlos
        binding.lanzarToastUsuario.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_feature_pending), Toast.LENGTH_SHORT).show()
        }

        binding.lanzarToastContrasena.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_feature_pending), Toast.LENGTH_SHORT).show()
        }
    }
    //crea el menú superior con dos opciones de idioma: español e inglés.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cambiar_lenguaje_menu, menu)
        return true
    }
//queria hacer la aplicacion bilingue, no solo desde los ajustes del telefono, queria en mi app un boton que
//pudieras darle click  y cambiar entre idiomas, y basicamente se actualiza la configuración de la aplicación para los idiomas
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language_es -> {
                setLocale("es")
                true
            }
            R.id.action_language_en -> {
                setLocale("en")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Esta función cambia el idioma de toda la aplicación en tiempo real.
    // Utiliza el metodo (AppCompatDelegate) para notificar
    // al sistema del nuevo idioma y reiniciar la interfaz de forma segura y sin parpadeos, que estos eran super molestos cuando lo hacia sin esto.
    private fun setLocale(languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}
