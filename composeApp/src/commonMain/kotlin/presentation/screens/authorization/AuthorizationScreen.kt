package presentation.screens.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.RequestState
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import presentation.screenViewModels.AuthorizationScreenViewModel
import presentation.screens.main.MainScreen

class AuthorizationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AuthorizationScreenViewModel = koinViewModel()

        val navigator = LocalNavigator.current

        val authState by viewModel.authState.collectAsStateWithLifecycle()
        val key by viewModel.key.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = key,
                    onValueChange = { newKey ->
                        viewModel.updateKey(newKey)
                    },
                    label = { Text(text = "Введите токен пользователя") },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.1f
                        ),
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                    )
                )

                if (authState is RequestState.Idle) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.logIn(key = key)
                        },
                        enabled = authState !is RequestState.Loading
                    ) {
                        Text("Войти")
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
                            navigator?.replaceAll(MainScreen(token = key))
                            viewModel.resetState()
                        }
                    }

                    is RequestState.Error -> {
                        val errorMessage = authState.getErrorMessage()
                        Text(
                            text = "Ошибка авторизации: $errorMessage",
                        )
                        Button(onClick = {
                            viewModel.logIn(key = key)
                        }) {
                            Text("Попробовать снова")
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { navigator?.pop() }
                ) {
                    Text(text = "Вернуться")
                }
            }
        }
    }
}