package com.micky.proyectv3.adapter

import android.util.Log
import com.micky.proyectv3.ModificarVideojuegoDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.micky.proyectv3.databinding.ItemVideojuegoBinding
import com.micky.proyectv3.models.Videojuego
import com.squareup.picasso.Picasso

class VideojuegoAdapter(
    var videojuegos: List<Videojuego>,
    private val onModifyClick: (Videojuego) -> Unit,  // 👈 Agregado
    private val onDeleteClick: (Videojuego) -> Unit
) : RecyclerView.Adapter<VideojuegoAdapter.VideojuegoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideojuegoViewHolder {
        val binding = ItemVideojuegoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideojuegoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideojuegoViewHolder, position: Int) {
        val videojuego = videojuegos[position]
        holder.bind(videojuego)
    }

    override fun getItemCount(): Int = videojuegos.size

    inner class VideojuegoViewHolder(private val binding: ItemVideojuegoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videojuego: Videojuego) {
            binding.nombre.text = videojuego.nombre
            binding.descripcion.text = videojuego.descripcion
            binding.categoria.text = videojuego.categoria

            Picasso.get()
                .load(videojuego.imagenUrl)
                .into(binding.imagen)

            binding.btnModificar.setOnClickListener {
                val activity = itemView.context as AppCompatActivity
                val dialog = ModificarVideojuegoDialogFragment(videojuego)
                dialog.show(activity.supportFragmentManager, "ModificarVideojuegoDialog")
            }

            binding.btnEliminar.setOnClickListener {
                Log.d("DEBUG", "Botón eliminar presionado para: ${videojuego.id}") // ✅ Verifica que se está llamando
                onDeleteClick(videojuego)
            }


            binding.btnModificar.setOnClickListener { onModifyClick(videojuego) }  // 👈 Ahora maneja modificación
            //binding.btnEliminar.setOnClickListener { onDeleteClick(videojuego) }

        }
    }
}
