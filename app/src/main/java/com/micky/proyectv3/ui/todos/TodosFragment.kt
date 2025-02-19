package com.micky.proyectv3.ui.todos

import com.micky.proyectv3.ModificarVideojuegoDialogFragment
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
            { videojuego -> mostrarDialogoModificar(videojuego) },  // ✅ Ahora sí lo acepta
            { videojuego ->
                videojuegoController.eliminarVideojuego(videojuego)
                videojuegoAdapter.notifyDataSetChanged() // ⚡ Asegurar actualización en la UI
            }

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
        val dialog = ModificarVideojuegoDialogFragment(videojuego)
        dialog.show(parentFragmentManager, "ModificarVideojuegoDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
