package presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import presentation.screens.access.AccessScreen

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        LaunchedEffect(Unit) {
            delay(3000)
            navigator?.replace(AccessScreen())
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "App",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
