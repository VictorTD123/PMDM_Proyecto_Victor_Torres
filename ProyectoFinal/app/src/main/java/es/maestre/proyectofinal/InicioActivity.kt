package es.maestre.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import es.maestre.proyectofinal.adapter.MenuAdapter
import es.maestre.proyectofinal.adapter.MenuProvider
import es.maestre.proyectofinal.databinding.ActivityInicioBinding

class InicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuro la barra de navegación inferior para que funcione.
        setSupportActionBar(binding.toolbarbotones)
        // Le quito el título, porque solo quiero que se vean los botones del menú.
        supportActionBar?.title = ""

        // Llamo a mi función para preparar y mostrar el menú principal.
        initRecyclerView()

        // Le pongo un listener al botón de "Qué es VitalFit".
        // Cuando se pulsa, abro la pantalla de "Acerca de" y la lanzo.
        binding.btnexplicarvitalfit.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
        }
    }

    // En esta función, preparo mi menú principal, que son botones organizados en forma de tabla.
    private fun initRecyclerView() {
        // Le digo que quiero una tabla de 2 columnas y 2 filas.
        binding.rvMenu.layoutManager = GridLayoutManager(this, 2)
        // Le asigno el adaptador, que cogerá la lista de botones de mi MenuProvider y los dibujará.
        binding.rvMenu.adapter = MenuAdapter(MenuProvider.menuItems)
    }

    // Aquí creo el menú de la barra de navegación inferior.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // creo  el diseño de mi inicio_menu.xml para que aparezcan los botones.
        menuInflater.inflate(R.menu.inicio_menu, menu)
        return true
    }

    // Aquí gestiono qué pasa cuando se pulsa un botón de la barra de navegación inferior.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Si pulso el botón de "Inicio", no hace nada porque ya estoy aquí.
            R.id.action_home -> {
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
            // Para cualquier otro caso,este super lo gestiona.
            else -> super.onOptionsItemSelected(item)
        }
    }
}
