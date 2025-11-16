package es.maestre.proyectofinal.adapter

import es.maestre.proyectofinal.R
import es.maestre.proyectofinal.model.MenuItem

object MenuProvider {
    val menuItems = arrayListOf(
        MenuItem(
            id = "profile",
            textResId = R.string.menu_item_profile,
            imageResId = R.drawable.ic_person_foreground
        ),
        MenuItem(
            id = "book_appointment",
            textResId = R.string.menu_item_book_appointment,
            imageResId = R.mipmap.ic_reservarcitas
        ),
        MenuItem(
            id = "my_progress",
            textResId = R.string.menu_item_my_progress,
            imageResId = R.drawable.ic_progreso
        ),
        MenuItem(
            id = "buy_material",
            textResId = R.string.menu_item_buy_material,
            imageResId = R.drawable.ic_compras_foreground
        )
    )
}
