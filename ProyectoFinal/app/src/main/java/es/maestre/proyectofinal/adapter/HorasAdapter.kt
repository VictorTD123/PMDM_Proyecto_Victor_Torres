package es.maestre.proyectofinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import es.maestre.proyectofinal.R
import es.maestre.proyectofinal.model.EstadoHora

// 1. El Adapter ahora recibe una función en su constructor
class HorasAdapter(
    private val horas: List<EstadoHora>,
    private val onHoraClicked: (EstadoHora) -> Unit 
) : RecyclerView.Adapter<HorasAdapter.HorasViewHolder>() {

    class HorasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.card_hora)
        val horaTextView: TextView = view.findViewById(R.id.tv_hora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_reservar_hora, parent, false)
        return HorasViewHolder(view)
    }

    override fun getItemCount(): Int = horas.size

    override fun onBindViewHolder(holder: HorasViewHolder, position: Int) {
        val estadoHora = horas[position]
        holder.horaTextView.text = estadoHora.hora

        val context = holder.itemView.context

        if (estadoHora.estaLibre) {
            holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.hora_libre))
            holder.card.isClickable = true
        } else {
            holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.hora_ocupada))
            holder.card.isClickable = false
        }

        holder.card.setOnClickListener { 
            if (estadoHora.estaLibre) {
                onHoraClicked(estadoHora)
            }
        }
    }
}