package com.micky.proyectv3.models

data class Videojuego(
    var nombre: String = "",
    var descripcion: String = "",
    var imagen: String = "",
    var categoria: String = ""
) {
    // Constructor vacío necesario para Firebase
    constructor() : this("", "", "", "")
}
