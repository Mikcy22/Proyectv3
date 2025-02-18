package com.micky.proyectv3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.micky.proyectv3.databinding.ItemVideojuegoBinding
import com.micky.proyectv3.models.Videojuego

import com.squareup.picasso.Picasso
/*
class VideojuegoAdapter(
    var videojuegos: List<Videojuego>,
    private val onItemClick: (Videojuego) -> Unit
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
            binding.categoria.text = videojuego.categoria  // Asignar la categoría

            // Cargar la imagen con Glide o Picasso
            Picasso.get()
                .load(videojuego.imagenUrl)
                .into(binding.imagen)

            binding.root.setOnClickListener { onItemClick(videojuego) }
        }
    }
}
*/

class VideojuegoAdapter(
    var videojuegos: List<Videojuego>,
    private val onItemClick: (Videojuego) -> Unit,
    private val onDeleteClick: (Videojuego) -> Unit,
    private val onEditClick: (Videojuego) -> Unit
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

            binding.root.setOnClickListener { onItemClick(videojuego) }

            binding.btnModificar.setOnClickListener { onEditClick(videojuego) }
            binding.btnEliminar.setOnClickListener { onDeleteClick(videojuego) }
        }
    }
}
