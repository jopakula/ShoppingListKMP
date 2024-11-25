package presentation.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import domain.RequestState
import org.koin.compose.viewmodel.koinViewModel
import presentation.viewModels.RegistrationViewModel

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegistrationViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()

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
