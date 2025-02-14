package com.micky.proyectv3.ui.deportes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeportesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "TODOS LOS VIDEOJUEGOS DE DEPORTES"
    }
    val text: LiveData<String> = _text
}