package com.levelup.levelupgamer.viewmodel.carrusel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarouselViewModel : ViewModel() {
    private val _urls = MutableStateFlow(listOf(
        "https://.../imagen1.jpg",
        "https://.../imagen2.jpg",
        "https://.../imagen3.jpg"
    ))
    val urls: StateFlow<List<String>> = _urls.asStateFlow()


}


