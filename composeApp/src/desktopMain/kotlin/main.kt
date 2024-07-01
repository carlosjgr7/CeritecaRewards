import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CeritecaReward",
        state = rememberWindowState(width = 1920.dp, height = 1080.dp, placement = WindowPlacement.Fullscreen)
        ) {
        App()
    }
}