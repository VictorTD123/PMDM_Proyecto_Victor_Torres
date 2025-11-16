package es.maestre.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.maestre.proyectofinal.databinding.ActivityAcercaDeBinding

class AcercaDeActivity : AppCompatActivity() {

    // Aquí guardo la referencia a mi diseño para poder acceder a las vistas.
    private lateinit var binding: ActivityAcercaDeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Primero, inflo el diseño de la pantalla (activity_acerca_de.xml) y lo conecto.
        binding = ActivityAcercaDeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuro mi barra de navegación inferior y le quito el título.
        setSupportActionBar(binding.toolbarbotones)
        supportActionBar?.title = ""
    }

    // Aquí creo el menú de la barra de navegación inferior.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflo el diseño de mi menú (inicio_menu.xml) para que aparezcan los botones.
        menuInflater.inflate(R.menu.inicio_menu, menu)
        return true
    }

    // Aquí gestiono qué pasa cuando pulso un botón de la barra de navegación.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Si pulso el botón de "Inicio", vuelvo a la pantalla principal.
            // Uso lo de flags para asegurar de que no se creen pantallas duplicadas.
            R.id.action_home -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }
            // Si pulso el botón de "Mis Citas", lanzo la pantalla correspondiente.
            R.id.action_citas -> {
                val intent = Intent(this, MisCitasActivity::class.java)
                startActivity(intent)
                true
            }
            // Para los otros dos botones, muestro un toast porque aún no tienen funcionalidad.
            R.id.action_button_3, R.id.action_button_4 -> {
                Toast.makeText(this, getString(R.string.toast_feature_pending), Toast.LENGTH_SHORT).show()
                true
            }
            // Para cualquier otro caso, uso el por defecto.
            else -> super.onOptionsItemSelected(item)
        }
    }
}
