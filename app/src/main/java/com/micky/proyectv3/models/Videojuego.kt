package com.micky.proyectv3.models


import com.google.firebase.firestore.PropertyName

data class Videojuego(
    @PropertyName("nombre") var nombre: String = "",
    @PropertyName("descripcion") var descripcion: String = "",
    @PropertyName("imagen_url") var imagenUrl: String = "",
    @PropertyName("categoria") var categoria: String = "",
    @PropertyName("id") var id: String = "" // ID mapeado explícitamente
) {

    // Constructor vacío necesario para Firebase
    constructor() : this("", "", "", "", "")
}


