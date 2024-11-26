import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.access.AccessScreen

@Composable
@Preview
fun App() {

    val darkColor1 = Color(color = 0xFF082F37)
    val darkColor2 = Color(color = 0xFF1AABDE)

    val lightColor1 = Color(color = 0xFF6BC2AD)
    val lightColor2 = Color(color = 0xFFFCFCFC)

    val lightColors = lightColorScheme(
        primary = lightColor1,
        onPrimary = lightColor2,
        primaryContainer = lightColor1,
        onPrimaryContainer = lightColor2
    )
    val darkColors = darkColorScheme(
        primary = darkColor1,
        onPrimary = darkColor2,
        primaryContainer = darkColor1,
        onPrimaryContainer = darkColor2
    )
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )
    MaterialTheme(colorScheme = colors) {
       Navigator(AccessScreen( ))
    }
}

