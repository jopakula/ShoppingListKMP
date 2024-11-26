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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.RequestState
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import presentation.screens.main.MainScreen
import presentation.viewModels.RegistrationViewModel

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegistrationViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is RequestState.Idle -> {
                    Button(onClick = { viewModel.fetchToken() }) {
                        Text("Получить токен")
                    }
                }
                is RequestState.Loading -> {
                    CircularProgressIndicator()
                }

                is RequestState.Success -> {
                    Text("Ваш токен: ${uiState.getSuccessData()}")
                    LaunchedEffect(Unit) {
                        delay(2000)
                        navigator?.push(MainScreen())
                        viewModel.resetState()
                    }
                }

                is RequestState.Error -> {
                    Text("Ошибка: ${uiState.getErrorMessage()}")
                    Button(onClick = { viewModel.fetchToken() }) {
                        Text("Попробовать снова")
                    }
                }
            }
        }
    }
}
