package com.micky.proyectv3.ui.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.micky.proyectv3.adapter.VideojuegoAdapter
import com.micky.proyectv3.controller.VideojuegoController
import com.micky.proyectv3.databinding.FragmentTodosBinding

import com.micky.proyectv3.R

import android.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import com.micky.proyectv3.models.Videojuego

class TodosFragment : Fragment() {

    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!

    private lateinit var videojuegoController: VideojuegoController
    private lateinit var videojuegoAdapter: VideojuegoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inicializar el ViewModel (Controller)
        videojuegoController = ViewModelProvider(this).get(VideojuegoController::class.java)

        // Configurar el RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar el adaptador
        videojuegoAdapter = VideojuegoAdapter(
            emptyList(),
            { videojuego ->
                // Manejar el clic en el videojuego para editar
                mostrarDialogoModificar(videojuego)
            },
            { videojuego ->
                // Llamar a eliminar el videojuego
                videojuegoController.eliminarVideojuego(videojuego)
            },
            { videojuego -> }
        )

        recyclerView.adapter = videojuegoAdapter

        // Observar los cambios en la lista de videojuegos
        videojuegoController.videojuegos.observe(viewLifecycleOwner) { videojuegos ->
            videojuegoAdapter.videojuegos = videojuegos
            videojuegoAdapter.notifyDataSetChanged()
        }

        // Cargar los videojuegos desde Firebase
        videojuegoController.cargarVideojuegos()

        return root
    }

    private fun mostrarDialogoModificar(videojuego: Videojuego) {
        // Crear el AlertDialog para modificar el videojuego
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Modificar Videojuego")

        // Inflar el layout del diálogo
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_modificar_videojuego, null)

        // Obtener referencias a los campos de texto
        val nombreInput = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val descripcionInput = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val categoriaInput = dialogView.findViewById<EditText>(R.id.editTextCategoria)

        // Rellenar los campos con los datos actuales
        nombreInput.setText(videojuego.nombre)
        descripcionInput.setText(videojuego.descripcion)
        categoriaInput.setText(videojuego.categoria)

        // Configurar el diálogo
        builder.setView(dialogView)

        builder.setPositiveButton("Guardar") { _, _ ->
            // Obtener los nuevos valores de los campos
            val nuevoNombre = nombreInput.text.toString()
            val nuevaDescripcion = descripcionInput.text.toString()
            val nuevaCategoria = categoriaInput.text.toString()

            // Validar los campos
            if (nuevoNombre.isNotBlank() && nuevaDescripcion.isNotBlank() && nuevaCategoria.isNotBlank()) {
                // Actualizar el videojuego con los nuevos valores
                videojuego.nombre = nuevoNombre
                videojuego.descripcion = nuevaDescripcion
                videojuego.categoria = nuevaCategoria

                // Llamar a la función de modificar en el controlador
                videojuegoController.modificarVideojuego(videojuego)

                // Mostrar un mensaje de éxito
                Toast.makeText(requireContext(), "Videojuego modificado con éxito", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar un mensaje de error si algún campo está vacío
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar", null)

        // Mostrar el diálogo
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

