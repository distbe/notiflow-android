package be.dist.notiflow.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.accompanist.insets.ProvideWindowInsets

private val LightThemeColors = lightColors(
    primary = Color.Grey800,
    primaryVariant = Color.Grey900,
    onPrimary = Color.White,
    secondary = Color.Indigo600,
    secondaryVariant = Color.Indigo800,
    onSecondary = Color.Black,
    error = Color.Red800
)

private val DarkThemeColors = darkColors(
    primary = Color.Grey400,
    primaryVariant = Color.Grey100,
    onPrimary = Color.Black,
    secondary = Color.Indigo400,
    secondaryVariant = Color.Indigo300,
    onSecondary = Color.White,
    error = Color.Red800
)

@Composable
fun BaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ProvideWindowInsets {
        MaterialTheme(
            colors = if (darkTheme) DarkThemeColors else LightThemeColors,
            content = content,
        )
    }
}


@Suppress("NOTHING_TO_INLINE")
inline fun ComponentActivity.setThemeContent(
    noinline content: @Composable () -> Unit
) = setContent {
    BaseTheme {
        Surface {
            content()
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Fragment.setThemeContent(
    noinline content: @Composable () -> Unit
) = ComposeView(requireContext()).apply {
    setContent {
        BaseTheme {
            content()
        }
    }
}
