package es.maestre.proyectofinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.maestre.proyectofinal.databinding.ItemCitaBinding
import es.maestre.proyectofinal.model.Cita

// 1. El Adapter ahora recibe la función .
class CitasAdapter(
    private var citas: List<Cita>,
    private val onCitaClicked: (Cita) -> Unit
) : RecyclerView.Adapter<CitasAdapter.CitasViewHolder>() {

    class CitasViewHolder(private val binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cita: Cita, onCitaClicked: (Cita) -> Unit) {
            binding.tvFechaCita.text = cita.fecha
            binding.tvHoraCita.text = cita.hora
            // 2. Añadimos el listener a la vista principal del item
            itemView.setOnClickListener { onCitaClicked(cita) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitasViewHolder(binding)
    }

    override fun getItemCount(): Int = citas.size

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int) {
        val cita = citas[position]
        // 3. Pasamos la cita y la función al ViewHolder
        holder.bind(cita, onCitaClicked)
    }

    fun updateCitas(nuevasCitas: List<Cita>) {
        citas = nuevasCitas
        notifyDataSetChanged()
    }
}
