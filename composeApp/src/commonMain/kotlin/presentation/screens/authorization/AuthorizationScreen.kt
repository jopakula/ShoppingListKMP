package presentation.screens.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
               .padding(16.dp),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
       ) {
           TextField(
               modifier = Modifier.fillMaxWidth(),
               value = key,
               onValueChange = { key ->
                   viewModel.updateKey(key)
               },
               label = { Text("Введите ключ") },

           )

           if (authState is RequestState.Idle ) {
               Button(
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
                       navigator?.push(MainScreen(token = key))
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
           Button(onClick = { navigator?.pop() }) {
               Text(text = "Вернуться")
           }
       }
    }
}