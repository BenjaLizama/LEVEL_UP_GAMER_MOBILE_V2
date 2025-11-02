package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.levelup.levelupgamer.ui.components.molecules.Carrusel
import com.levelup.levelupgamer.viewmodel.carrusel.CarouselViewModel


@Composable
fun PantallaCarrusel(viewModel: CarouselViewModel = viewModel()) {
    /*val litaImagenes=listOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3,
        R.drawable.imagen4

    )*/
    val listaUrls by viewModel.urls.collectAsState()
    val pagerState = rememberPagerState(pageCount = { listaUrls.size }
    )
    Carrusel(
        items = listaUrls,
        pagerState = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { urlDeLaImagen ->
        AsyncImage(
            model = urlDeLaImagen,
            contentDescription = "imagen del carrusel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}