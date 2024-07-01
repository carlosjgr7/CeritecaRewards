package multiplatformFunctions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import getConfig

enum class Platforms {
    Desktop,
    Android,
    IOS,
    Web
}

class ConfigDevice {
    companion object {
        private val sizeDevice = getConfig()

        fun getsizedivice(): TextUnit {
            return sizeDevice.size
        }

        fun getspacerdivice(): Dp {
            return sizeDevice.spacer
        }

        fun getsubtitlesizedivice(): TextUnit {
            return sizeDevice.subtitle
        }

        fun getplatform(): Platforms {
            return when (sizeDevice.title) {
                "Desktop" -> Platforms.Desktop
                "Android" -> Platforms.Android
                "IOS" -> Platforms.IOS
                "Web" -> Platforms.Web
                else -> Platforms.Desktop
            }
        }
    }

}

