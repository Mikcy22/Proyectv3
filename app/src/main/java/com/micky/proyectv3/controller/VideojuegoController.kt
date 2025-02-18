package com.micky.proyectv3.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.micky.proyectv3.models.Videojuego

//class VideojuegoController : ViewModel() {
//
//    private val db = FirebaseFirestore.getInstance()
//    private val _videojuegos = MutableLiveData<List<Videojuego>>()
//    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos
//
//    fun cargarVideojuegos() {
//        db.collection("videojuegos")
//            .get()
//            .addOnSuccessListener { result ->
//                val listaVideojuegos = result.toObjects(Videojuego::class.java)
//                _videojuegos.value = listaVideojuegos
//            }
//            .addOnFailureListener { exception ->
//                // Manejar el error
//            }
//    }
//}


class VideojuegoController : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _videojuegos = MutableLiveData<List<Videojuego>>()
    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos

    // Cargar videojuegos desde Firebase
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

    // Método para agregar un nuevo videojuego a Firestore
    fun añadirVideojuego(videojuego: Videojuego) {
        // Usamos add() para generar un ID único automáticamente
        db.collection("videojuegos")
            .add(videojuego)
            .addOnSuccessListener { documentReference ->
                // Cuando el documento se ha añadido, actualizamos el ID
                val idGenerado = documentReference.id
                videojuego.id = idGenerado

                // Opcional: Actualizar el objeto en Firestore con el ID
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


    // Función para modificar un videojuego en Firestore
    fun modificarVideojuego(videojuego: Videojuego) {
        // Usamos el ID del videojuego para ubicar el documento a modificar
        val documentRef = db.collection("videojuegos").document(videojuego.id)

        // Actualizamos el documento en Firestore
        documentRef.set(videojuego)
            .addOnSuccessListener {
                println("Videojuego modificado con ID: ${videojuego.id}")
            }
            .addOnFailureListener { e ->
                println("Error al modificar el videojuego: $e")
            }
    }

    // Método para eliminar un videojuego usando el ID generado
    fun eliminarVideojuego(videojuego: Videojuego) {
        val docRef = db.collection("videojuegos").document(videojuego.id) // Usar el ID almacenado
        docRef.delete()
            .addOnSuccessListener {
                // Acción cuando se elimina correctamente
            }
            .addOnFailureListener { e ->
                // Acción si ocurre un error
            }
    }


}
