package es.maestre.proyectofinal.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.Serializable

data class MenuItem(
    val id: String,
    @StringRes val textResId: Int,
    @DrawableRes val imageResId: Int
) : Serializable
