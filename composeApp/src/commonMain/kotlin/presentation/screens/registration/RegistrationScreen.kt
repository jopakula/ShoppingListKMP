package presentation.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.RequestState
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import presentation.screenViewModels.RegistrationScreenViewModel
import presentation.screens.main.MainScreen

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegistrationScreenViewModel = koinViewModel()

        val navigator = LocalNavigator.current

        val keyState by viewModel.keyState.collectAsStateWithLifecycle()
        val authState by viewModel.authState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (keyState) {
                is RequestState.Idle -> {
                    Button(onClick = { viewModel.fetchToken() }) {
                        Text("Получить токен")
                    }
                }
                is RequestState.Loading -> {
                    CircularProgressIndicator()
                }

                is RequestState.Success -> {
                    Text("Ваш токен: ${keyState.getSuccessData()}")
                    LaunchedEffect(keyState.getSuccessData()) {
                        delay(1000)
                        viewModel.logIn(keyState.getSuccessData())
                    }
                }

                is RequestState.Error -> {
                    Text("Ошибка: ${keyState.getErrorMessage()}")
                    Button(onClick = { viewModel.fetchToken() }) {
                        Text("Попробовать снова")
                    }
                }
            }

            when (authState) {
                is RequestState.Idle -> {}
                is RequestState.Loading -> {
                    CircularProgressIndicator()
                }
                is RequestState.Success -> {
                    Text("Успешный вход!")

                    LaunchedEffect(Unit) {
                        delay(1000)
                        navigator?.replaceAll(MainScreen(token = keyState.getSuccessData()))
                        viewModel.resetState()
                    }
                }
                is RequestState.Error -> {
                    Text("Ошибка авторизации: ${authState.getErrorMessage()}")
                    Button(onClick = {
                        viewModel.logIn(keyState.getSuccessData())
                    }) {
                        Text("Попробовать снова")
                    }
                }
            }
            Button(onClick = { navigator?.pop() }) {
                Text(text = "Вернуться")
            }
        }
    }
}
