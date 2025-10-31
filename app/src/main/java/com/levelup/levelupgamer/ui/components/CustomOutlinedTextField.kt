package com.levelup.levelupgamer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levelup.levelupgamer.ui.theme.ColorAcento
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    mensajeError: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = !mensajeError.isNullOrEmpty(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = ColorTextoPrincipal,
                focusedLabelColor = ColorAcento,
                focusedIndicatorColor = ColorAcento,

                unfocusedTextColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,

                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = ColorAcento
            )
        )

        if (mensajeError != null) {
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    modifier = Modifier
                        .background(ColorFondo)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}