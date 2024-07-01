package parameters.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview


class ParameterScreen : Screen {
    @Preview
    @Composable
    override fun Content() {

        Column(Modifier.fillMaxSize()) {
            Row(Modifier.weight(1f)) {
                Column(
                    Modifier.fillMaxSize().weight(1f).background(Color.Yellow)
                ) {
                    Text(text = "Meta Mensual")
                }
                Column(
                    Modifier.fillMaxSize().weight(1f).background(Color.Blue)
                ) {
                    Text(text = "mes/meta")
                }
            }
            Divider( Modifier.fillMaxWidth().background(Color(0xFFCDCDCD)))
            Row(Modifier.weight(1f)) {
                Column(
                    Modifier.fillMaxSize().weight(1f).background(Color.Red)
                ) {
                    Text(text = "ventas diarias")
                }
                Column(
                    Modifier.fillMaxSize().weight(1f).background(Color.Green)
                ) {
                    Text(text = "Dia/venta")
                }
            }

            val dialogState = remember { mutableStateOf(false) }
            Button(onClick = { dialogState.value = true }) {
                Text("show dialog")
            }

            if (dialogState.value) {
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        Button(onClick = { dialogState.value = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Alert Dialog") },
                    text = { Text("Lore ipsum") },
                )
            }

        }

    }
}
