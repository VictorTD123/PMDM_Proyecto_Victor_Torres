package es.maestre.proyectofinal.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RespuestaSalud(
    val slip: ConsejoSalud
)

@Serializable
data class ConsejoSalud(
    val id: Int,
    @SerialName("advice") val consejo: String
)
