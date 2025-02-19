package com.micky.proyectv3

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.micky.proyectv3.controller.VideojuegoController
import com.micky.proyectv3.models.Videojuego

class ModificarVideojuegoDialogFragment(
    private val videojuego: Videojuego
) : DialogFragment() {

    private lateinit var videojuegoController: VideojuegoController

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_modificar_videojuego, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videojuegoController = VideojuegoController()

        val nombreInput = view.findViewById<EditText>(R.id.editTextNombre)
        val descripcionInput = view.findViewById<EditText>(R.id.editTextDescripcion)
        val categoriaInput = view.findViewById<EditText>(R.id.editTextCategoria)
        val imagenUrlInput = view.findViewById<EditText>(R.id.editTextImagenUrl)

        val btnGuardar = view.findViewById<Button>(R.id.btnModificar)
        val btnCancelar = view.findViewById<Button>(R.id.btnCancelar)

        // Rellenar con los datos actuales del videojuego
        nombreInput.setText(videojuego.nombre)
        descripcionInput.setText(videojuego.descripcion)
        categoriaInput.setText(videojuego.categoria)
        imagenUrlInput.setText(videojuego.imagenUrl)


        btnGuardar.setOnClickListener {
            val nuevoNombre = nombreInput.text.toString()
            val nuevaDescripcion = descripcionInput.text.toString()
            val nuevaCategoria = categoriaInput.text.toString()
            val nuevaImagenUrl = imagenUrlInput.text.toString()


            if (nuevoNombre.isNotBlank() && nuevaDescripcion.isNotBlank() && nuevaCategoria.isNotBlank()) {
                videojuego.nombre = nuevoNombre
                videojuego.descripcion = nuevaDescripcion
                videojuego.categoria = nuevaCategoria
                videojuego.imagenUrl = nuevaImagenUrl

                videojuegoController.actualizarVideojuego(videojuego)
                dismiss()
            }
        }

        btnCancelar.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Fondo transparente
        return dialog
    }
}
