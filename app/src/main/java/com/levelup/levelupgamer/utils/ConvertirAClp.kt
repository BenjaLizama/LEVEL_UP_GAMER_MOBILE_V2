package com.levelup.levelupgamer.utils
import java.text.NumberFormat
import java.util.Locale


fun formatPriceToCLP(price: Double): String {

    val locale = Locale("es", "CL")


    val formatter = NumberFormat.getCurrencyInstance(locale)


    formatter.maximumFractionDigits = 0


    return formatter.format(price)
}
