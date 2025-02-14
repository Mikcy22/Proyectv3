package com.micky.proyectv3.ui.arcade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArcadeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "TODOS LOS VIDEOJUEGOS DE ARCADE"
    }
    val text: LiveData<String> = _text
}