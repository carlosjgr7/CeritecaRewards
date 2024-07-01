import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


actual fun getConfig(): PlatformSize {
    return object : PlatformSize {
        override val size: TextUnit = 140.sp
        override val spacer: Dp
            get() = 64.dp
        override val subtitle: TextUnit
            get() = 64.sp
        override val title: String
            get() = "Desktop"
    }
}