package es.maestre.proyectofinal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.maestre.proyectofinal.R
import es.maestre.proyectofinal.ReservarCitaActivity
import es.maestre.proyectofinal.databinding.ItemMenuBinding
import es.maestre.proyectofinal.model.MenuItem

class MenuAdapter(private val menuItems: List<MenuItem>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMenuBinding.bind(view)
        val image: ImageView = binding.ivIcono
        val name: TextView = binding.tvNombre
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

   //Le digo al RecyclerView cuántos botones hay en mi lista.
    override fun getItemCount(): Int = menuItems.size

    // Esta es la función más importante. Se llama para cada botón de la rejilla.
    // Aquí es donde cojo los datos de un botón (su texto, su icono) y los "pinto" en el molde.
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // Cojo los datos del botón que toca dibujar.
        val item = menuItems[position]
        val context = holder.itemView.context

        // Le pongo su texto (ej: "Perfil") y su imagen (ej: el icono de la persona).
        holder.name.text = context.getString(item.textResId)
        holder.image.setImageResource(item.imageResId)

        // Y lo más importante: aquí le digo qué hacer cuando el usuario pulse el botón.
        holder.itemView.setOnClickListener { 
            // Uso un 'when' para decidir la acción según el 'id' que le puse a cada botón en mi MenuProvider.
            when (item.id) {
                // Si el id es "profile", "my_progress" o "buy_material", muestro un toast.
                "profile", "my_progress", "buy_material" -> {
                    Toast.makeText(context, context.getString(R.string.toast_temporarily_unavailable), Toast.LENGTH_SHORT).show()
                }
                // Si el id es "book_appointment", lanzo la pantalla para reservar una cita.
                "book_appointment" -> {
                    val intent = Intent(context, ReservarCitaActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}
