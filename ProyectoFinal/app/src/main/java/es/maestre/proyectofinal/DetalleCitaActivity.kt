package es.maestre.proyectofinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import es.maestre.proyectofinal.databinding.ActivityDetalleCitaBinding
import es.maestre.proyectofinal.model.Cita

class DetalleCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleCitaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        recuperarYMostrarCita()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarbotones)
        supportActionBar?.title = ""
    }

    // en esta fucnion recupero y muestro la cita que se pulso.
    private fun recuperarYMostrarCita() {
        // La información de la cita llega a través del Intent.
        // Hago una comprobación de la versión de Android para usar el metodo correcto, ya que sin esto daba errores.
        val cita = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("CITA_SELECCIONADA", Cita::class.java)
        } else {
            // Para versiones antiguas de Android, uso el metodo este que esta obsoleto.
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("CITA_SELECCIONADA") as Cita?
        }

        // Si he conseguido recuperar la cita, pongo sus datos (fecha y hora) en los TextViews para que se vean.
        if (cita != null) {
            binding.tvValorFecha.text = cita.fecha
            binding.tvValorHora.text = cita.hora
        }
    }

//esta es la misma toolbar que antes
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
                finish()
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
