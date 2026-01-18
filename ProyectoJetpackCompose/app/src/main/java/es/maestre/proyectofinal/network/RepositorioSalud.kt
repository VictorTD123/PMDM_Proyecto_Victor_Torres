package es.maestre.proyectofinal.network

import es.maestre.proyectofinal.model.RespuestaSalud
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RepositorioSalud(private val client: HttpClient) {
    suspend fun getConsejo(): RespuestaSalud {
        // Llamamos a la API  que en este caso es de consejos de salud
        return client.get(urlString = "https://api.adviceslip.com/advice").body()
    }
}
