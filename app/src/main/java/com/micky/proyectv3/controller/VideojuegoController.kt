package com.micky.proyectv3.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.micky.proyectv3.models.Videojuego

class VideojuegoController : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _videojuegos = MutableLiveData<List<Videojuego>>()
    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos

    fun cargarVideojuegos() {
        db.collection("videojuegos")
            .get()
            .addOnSuccessListener { result ->
                val listaVideojuegos = result.toObjects(Videojuego::class.java)
                _videojuegos.value = listaVideojuegos
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }
}