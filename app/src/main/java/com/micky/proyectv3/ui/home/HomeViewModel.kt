package com.micky.proyectv3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "HOME AQUI VA MMUCHA MIERDA"
    }
    val text: LiveData<String> = _text
}