package com.micky.proyectv3.ui.accion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "TODOS LOS VIDEOJUEGOS DE ACCION"
    }
    val text: LiveData<String> = _text
}