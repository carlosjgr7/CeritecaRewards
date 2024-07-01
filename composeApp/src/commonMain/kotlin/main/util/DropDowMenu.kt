package main.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MesesDropdownMenu(
    mesPorDefecto: String,
    modifier: Modifier,
    onMesSeleccionado: (String) -> Unit = {}
) {
    val meses = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedMes by remember { mutableStateOf(mesPorDefecto) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = selectedMes,
            onValueChange = {
                selectedMes = it
            },
            label = { Text("Mes") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
            }
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            meses.forEach { mes ->
                DropdownMenuItem(onClick = {
                    selectedMes = mes
                    onMesSeleccionado(selectedMes)
                    expanded = false
                }) {
                    Text(mes)
                }
            }
        }
    }
}