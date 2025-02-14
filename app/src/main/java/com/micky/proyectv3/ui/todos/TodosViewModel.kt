package com.micky.proyectv3.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "TODOS LOS VIDEOJUEGOS"
    }
    val text: LiveData<String> = _text
}