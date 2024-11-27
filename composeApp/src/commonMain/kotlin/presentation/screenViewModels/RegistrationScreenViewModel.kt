package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.AuthResponseModel
import domain.useCases.AuthorizationUseCase
import domain.useCases.FetchRegistrationTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationScreenViewModel(
    private val fetchRegistrationTokenUseCase: FetchRegistrationTokenUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
) : ViewModel() {

    private val _keyState = MutableStateFlow<RequestState<String>>(RequestState.Idle)
    val keyState: StateFlow<RequestState<String>> = _keyState

    private val _authState = MutableStateFlow<RequestState<AuthResponseModel>>(RequestState.Idle)
    val authState: StateFlow<RequestState<AuthResponseModel>> = _authState

    fun fetchToken() {
        viewModelScope.launch {
            _keyState.value = RequestState.Loading
            runCatching {
                fetchRegistrationTokenUseCase.execute()
            }.onSuccess { token ->
                _keyState.value = RequestState.Success(token)
            }.onFailure { exception ->
                _keyState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun logIn(key: String){
        viewModelScope.launch {
            _authState.value = RequestState.Loading
            runCatching {
                authorizationUseCase.execute(key = key)
            }.onSuccess { response ->
                _authState.value = RequestState.Success(response)
            }.onFailure { exception ->
                _authState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _keyState.value = RequestState.Idle
        _authState.value = RequestState.Idle
    }
}
