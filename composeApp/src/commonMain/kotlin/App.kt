import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import main.presentation.MainScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(MainScreen()){ navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}