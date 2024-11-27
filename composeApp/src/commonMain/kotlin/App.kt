import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.access.AccessScreen
import presentation.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        Navigator(AccessScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
