package presentation.screens.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.screens.authorization.AuthorizationScreen
import presentation.screens.registration.RegistrationScreen

class AccessScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { navigator?.push(AuthorizationScreen()) }
                ) {
                    Text(text = "Войти")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { navigator?.push(RegistrationScreen()) }
                ) {
                    Text(text = "Зарегистрироваться")
                }
            }
        }
    }
}