package presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.AuthResponseModel
import domain.useCases.AuthorizationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val authorizationUseCase: AuthorizationUseCase,
): ViewModel() {

    private val _authState = MutableStateFlow<RequestState<AuthResponseModel>>(RequestState.Idle)
    val authState: StateFlow<RequestState<AuthResponseModel>> = _authState

    private val _key = MutableStateFlow("")
    val key: StateFlow<String> = _key

    fun logIn(key: String){
        viewModelScope.launch {
            _authState.value = RequestState.Loading
            runCatching {
                authorizationUseCase.execute(key = key)
            }.onSuccess { response ->
                _authState.value = RequestState.Success(response)
            }.onFailure { exception ->

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