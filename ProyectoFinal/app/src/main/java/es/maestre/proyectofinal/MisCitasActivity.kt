package es.maestre.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.maestre.proyectofinal.adapter.CitasAdapter
import es.maestre.proyectofinal.databinding.ActivityMisCitasBinding
import es.maestre.proyectofinal.viewmodel.MisCitasViewModel

class MisCitasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMisCitasBinding
    private val viewModel: MisCitasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisCitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Llamo a mis funciones para configurar las diferentes partes de la pantalla.
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarbotones)
        supportActionBar?.title = ""
    }

    // Aquí preparo la lista donde mostraré todas las citas que he reservado.
    private fun setupRecyclerView() {
        // Creo el adaptador, que es el que sabrá cómo dibujar cada cita.
        // Al principio le paso una lista vacía, porque los datos los cargaré después.
        val adapter = CitasAdapter(emptyList()) { citaPulsada ->
            // A cada tarjeta de cita que cree el adaptador, tiene un proceso:
            // primero abre la pantalla de detalle (DetalleCitaActivity),
            // y mete en ella los datos de la cita que se ha pulsado.".
            val intent = Intent(this, DetalleCitaActivity::class.java)
            intent.putExtra("CITA_SELECCIONADA", citaPulsada)
            startActivity(intent)
        }
        // Le asigno el adaptador a mi RecyclerView.
        binding.rvMisCitas.adapter = adapter
        // Le digo que quiero que las citas se muestren en una lista vertical.
        binding.rvMisCitas.layoutManager = LinearLayoutManager(this)
    }

    // En esta función, reflejo  cualquier cambio en los datos de mi ViewModel.
    private fun observeViewModel() {
        // El 'observe' se activa automáticamente cuando el ViewModel obtiene la lista de citas de la base de datos.
        viewModel.todasLasCitas.observe(this) { citas ->
            // Cuando me llega la lista de citas, me aseguro de que no sea nula.
            citas?.let {
                // Le paso la nueva lista de citas a mi adaptador para que actualice la pantalla y las muestre.
                (binding.rvMisCitas.adapter as CitasAdapter).updateCitas(it)
            }
        }
    }

    // Aquí creo la misma toolbar que en las otras pantallas.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.inicio_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }
            R.id.action_citas -> {
                // Si pulso el botón de "Mis Citas", no hago nada porque ya estoy aquí.
                true
            }
            R.id.action_button_3, R.id.action_button_4 -> {
                Toast.makeText(this, getString(R.string.toast_feature_pending), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
