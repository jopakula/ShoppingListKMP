package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.AuthResponseModel
import domain.useCases.AuthorizationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthorizationScreenViewModel(
    private val authorizationUseCase: AuthorizationUseCase,
): ViewModel() {

    private val _authState = MutableStateFlow<RequestState<AuthResponseModel>>(RequestState.Idle)
    val authState: StateFlow<RequestState<AuthResponseModel>> = _authState

    private val _key = MutableStateFlow("")
    val key: StateFlow<String> = _key

    fun logIn(key: String) {
        viewModelScope.launch {
            _authState.value = RequestState.Loading
            runCatching {
                authorizationUseCase.execute(key = key)
            }.onSuccess { response ->
                if (response.success) {
                    _authState.value = RequestState.Success(response)  // Обновляем состояние на успешный результат
                    println(RequestState.Success(response))
                } else {
                    _authState.value = RequestState.Error("Invalid key or authentication failed")  // Обновляем состояние на ошибку
                    println(RequestState.Error("Invalid key or authentication failed"))
                }
            }.onFailure { exception ->
                _authState.value = RequestState.Error(exception.message ?: "Unknown error")  // Обновляем состояние на ошибку
                println(RequestState.Error(exception.message ?: "Unknown error"))
            }
        }
    }

    fun updateKey(newKey: String) {
        _key.value = newKey
    }

    fun resetState() {
        _authState.value = RequestState.Idle
        _key.value = ""
    }
}