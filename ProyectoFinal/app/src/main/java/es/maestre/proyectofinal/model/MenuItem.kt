package es.maestre.proyectofinal.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.Serializable

data class MenuItem(
    val id: String, // Un identificador único que no cambia con el idioma
    @StringRes val textResId: Int, // La referencia (Int) al texto, ej: R.string.menu_perfil
    @DrawableRes val imageResId: Int // La referencia (Int) a la imagen, ej: R.drawable.ic_person
) : Serializable
