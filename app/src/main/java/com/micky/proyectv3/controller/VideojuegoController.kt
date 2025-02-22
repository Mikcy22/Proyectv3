package com.micky.proyectv3.controller

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.micky.proyectv3.models.Videojuego

class VideojuegoController : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _videojuegos = MutableLiveData<List<Videojuego>>()
    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos

    // Cargar videojuegos desde Firebase
    fun cargarVideojuegos() {
        db.collection("videojuegos")
            .addSnapshotListener { result, exception ->
                if (exception != null) {
                    println("Error al obtener videojuegos: $exception")
                    return@addSnapshotListener
                }

                val listaVideojuegos = mutableListOf<Videojuego>()
                for (document in result!!) {
                    val vj = document.toObject(Videojuego::class.java)
                    vj.id = document.id  // Asignar ID desde Firestore
                    listaVideojuegos.add(vj)
                }
                _videojuegos.value = listaVideojuegos  // ⚡ Actualiza en tiempo real
            }
    }


    /*
    fun cargarVideojuegosAccion() {
        db.collection("videojuegos")
            .whereEqualTo("categoria", "Accion") // Filtrar por categoría
            .addSnapshotListener { result, exception ->
                if (exception != null) {
                    Log.e("Firestore", "Error al obtener videojuegos de acción: $exception")
                    return@addSnapshotListener
                }

                val listaVideojuegos = mutableListOf<Videojuego>()
                for (document in result!!) {
                    val vj = document.toObject(Videojuego::class.java)
                    vj.id = document.id  // Asignar ID desde Firestore
                    listaVideojuegos.add(vj)
                }
                _videojuegos.value = listaVideojuegos  // Actualiza en tiempo real
            }
    }*/


    fun cargarVideojuegosCategoria(categoria: String){
        db.collection("videojuegos")
            .whereEqualTo("categoria", categoria) // Filtrar por categoría
            .addSnapshotListener { result, exception ->
                if (exception != null) {
                    Log.e("Firestore", "Error al obtener videojuegos de acción: $exception")
                    return@addSnapshotListener
                }

                val listaVideojuegos = mutableListOf<Videojuego>()
                for (document in result!!) {
                    val vj = document.toObject(Videojuego::class.java)
                    vj.id = document.id  // Asignar ID desde Firestore
                    listaVideojuegos.add(vj)
                }
                _videojuegos.value = listaVideojuegos  // Actualiza en tiempo real
            }
    }




    // Método para agregar un nuevo videojuego a Firestore
    fun añadirVideojuego(videojuego: Videojuego) {
        db.collection("videojuegos")
            .add(videojuego)
            .addOnSuccessListener { documentReference ->
                val idGenerado = documentReference.id
                videojuego.id = idGenerado
                db.collection("videojuegos").document(idGenerado)
                    .set(videojuego)
                    .addOnSuccessListener {
                        println("Videojuego agregado con ID: $idGenerado")
                    }
                    .addOnFailureListener { e ->
                        println("Error al actualizar el videojuego: $e")
                    }
            }
            .addOnFailureListener { e ->
                println("Error al agregar el videojuego: $e")
            }
    }

    // Función para actualizar (modificar) un videojuego en Firestore
    fun actualizarVideojuego(videojuego: Videojuego) {
        if (videojuego.id.isEmpty()) {
            println("Error: El id del videojuego está vacío. No se puede actualizar.")
            return
        }
        db.collection("videojuegos").document(videojuego.id)
            .set(videojuego)
            .addOnSuccessListener {
                println("Videojuego actualizado correctamente con ID: ${videojuego.id}")
                cargarVideojuegos() // ✅ Recargar lista tras modificar
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el videojuego: $e")
            }
    }

    // Método para eliminar un videojuego usando el ID generado
    fun eliminarVideojuego(videojuego: Videojuego) {
        if (videojuego.id.isEmpty()) {
            Log.e("Firestore", "Error: Intentando eliminar un videojuego sin ID")
            return
        }

        val docRef = db.collection("videojuegos").document(videojuego.id)

        docRef.delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Videojuego eliminado correctamente: ${videojuego.id}")
                cargarVideojuegos() // Actualizar la lista tras eliminar
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al eliminar el videojuego", e)
            }
    }


}
