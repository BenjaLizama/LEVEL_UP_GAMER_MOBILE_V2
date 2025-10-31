package com.levelup.levelupgamer.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

const val FILE_PROVIDER_AUTHORITY = "com.levelup.levelupgamer.fileprovider"


fun crearUriTemporal(context: Context): Uri {
    val cachePath = File(context.externalCacheDir, "profile_images")
    cachePath.mkdirs()

    val file = try {
        File.createTempFile(
            "temp_photo_",
            ".jpg",
            cachePath
        )
    } catch (e: IOException) {
        throw RuntimeException("No se pudo crear el archivo temporal para la cámara", e)
    }

    return FileProvider.getUriForFile(
        context,
        FILE_PROVIDER_AUTHORITY,
        file
    )
}