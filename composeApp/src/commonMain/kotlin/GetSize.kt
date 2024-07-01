import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

interface PlatformSize {
    val size: TextUnit
    val spacer: Dp
    val subtitle: TextUnit
    val title: String
}


expect fun getConfig(): PlatformSize