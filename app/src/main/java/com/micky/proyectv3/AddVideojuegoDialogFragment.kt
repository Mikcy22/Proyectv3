package com.micky.proyectv3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.micky.proyectv3.databinding.DialogAddVideojuegoBinding
import com.micky.proyectv3.models.Videojuego
import com.micky.proyectv3.controller.VideojuegoController

class AddVideojuegoDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddVideojuegoBinding
    private var controlador: VideojuegoController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddVideojuegoBinding.inflate(inflater, container, false)
        controlador = ViewModelProvider(requireActivity()).get(VideojuegoController::class.java)

        // Acción cuando se hace clic en el botón para añadir el videojuego
        binding.btnAnadir.setOnClickListener {
            val nombre = binding.editTextNombre.text.toString()
            val descripcion = binding.editTextDescripcion.text.toString()
            val categoria = binding.editTextCategoria.text.toString()
            val imagenUrl = binding.editTextImagenUrl.text.toString()

            // Validar que no haya campos vacíos
            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && categoria.isNotEmpty() && imagenUrl.isNotEmpty()) {
                val videojuego = Videojuego(nombre, descripcion, imagenUrl, categoria)
                controlador?.añadirVideojuego(videojuego)
                dismiss() // Cerrar el diálogo
            } else {
                // Mostrar un mensaje de error si algún campo está vacío
                Toast.makeText(requireContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
