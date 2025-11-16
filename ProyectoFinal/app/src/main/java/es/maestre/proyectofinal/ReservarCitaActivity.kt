package es.maestre.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import es.maestre.proyectofinal.adapter.HorasAdapter
import es.maestre.proyectofinal.databinding.ActivityReservarCitaBinding
import es.maestre.proyectofinal.model.Cita
import es.maestre.proyectofinal.model.EstadoHora
import es.maestre.proyectofinal.viewmodel.ReservarCitaViewModel
import java.util.Calendar

class ReservarCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservarCitaBinding
    private val viewModel: ReservarCitaViewModel by viewModels()

    // Guardo aquí la fecha que el usuario elija para poder usarla mas adelante.
    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Primero, inflo el diseño y lo conecto a la pantalla.
        binding = ActivityReservarCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Llamo a mis funciones para configurar todas las partes de la pantalla.
        setupToolbar()
        setupListeners()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarbotones)
        supportActionBar?.title = ""
    }

    // Configuro los listeners para que, al pulsar el campo de fecha o el icono, se abra el calendario.
    private fun setupListeners() {
        binding.etFechaSeleccionada.setOnClickListener { showDatePickerDialog() }
        binding.btnSeleccionarFecha.setOnClickListener { showDatePickerDialog() }
    }

    // Aquí preparo la cuadricula donde mostraré las horas disponibles.
    private fun setupRecyclerView() {
        // Le digo que quiero una tabla de 3 columnas.
        binding.rvHoras.layoutManager = GridLayoutManager(this, 3)
    }

    // Esta es la función más importante, es la que conecta la pantalla con el "cerebro" (ViewModel).
    // Me quedo "escuchando" cualquier cambio en la lista de horas que mi ViewModel calcula.
    private fun observeViewModel() {
        // El 'observe' se activa automáticamente cuando el ViewModel termina de calcular las horas
        // y me entrega la 'listaDeEstados' (un listado de todas las horas y si están libres u ocupadas).
        viewModel.estadoHoras.observe(this) { listaDeEstados ->
            // Con la lista nueva, creo mi adaptador de horas ("HorasAdapter"), que es el que
            // pinta cada botón de hora en la tabla.
            binding.rvHoras.adapter = HorasAdapter(listaDeEstados) { horaPulsada ->
                // A cada botón de hora que crea el adaptador, le doy una instrucción:
                // "Cuando alguien te pulse, llama a mi función 'mostrarDialogoConfirmacion'
                // y pásale la hora que ha sido pulsada".
                mostrarDialogoConfirmacion(horaPulsada)
            }
        }
    }

    // Aqui  creo y muestro el diálogo para confirmar la reserva.
    private fun mostrarDialogoConfirmacion(estadoHora: EstadoHora) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Reserva")
            .setMessage("¿Seguro que quieres reservar una cita el día $fechaSeleccionada a las ${estadoHora.hora}?")
            .setPositiveButton("Aceptar") { _, _ ->
                // Si pulso "Aceptar", creo el objeto Cita y le pido a mi ViewModel que lo guarde en la base de datos.
                val nuevaCita = Cita(fecha = fechaSeleccionada, hora = estadoHora.hora, reservada = true)
                viewModel.insertarCita(nuevaCita)
            }
            .setNegativeButton("Cancelar", null) // Si pulso "Cancelar", no hago nada.
            .show()
    }

    // Esta función se encarga de mostrar el calendario para que yo elija una fecha.
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Cuando elijo una fecha, la guardo en mi variable y en el campo de texto.
                fechaSeleccionada = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.etFechaSeleccionada.setText(fechaSeleccionada)
                // Le pido a mi ViewModel que calcule y me dé las horas disponibles para esa fecha.
                viewModel.cargarEstadoHoras(fechaSeleccionada)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    // Aquí creo el menú de la Toolbar.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.inicio_menu, menu)
        return true
    }

    // Aquí gestiona qué pasa cuando pulso un botón de la barra de navegación.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }
            R.id.action_citas -> {
                val intent = Intent(this, MisCitasActivity::class.java)
                startActivity(intent)
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
