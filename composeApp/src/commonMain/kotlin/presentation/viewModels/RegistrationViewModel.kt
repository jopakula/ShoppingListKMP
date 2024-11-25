package presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.useCases.GetRegistrationTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val getRegistrationTokenUseCase: GetRegistrationTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RequestState<String>>(RequestState.Idle)
    val uiState: StateFlow<RequestState<String>> = _uiState

    fun fetchToken() {
        viewModelScope.launch {
            _uiState.value = RequestState.Loading
            runCatching {
                getRegistrationTokenUseCase.execute()
            }.onSuccess { token ->
                _uiState.value = RequestState.Success(token)
            }.onFailure { exception ->
                _uiState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}
