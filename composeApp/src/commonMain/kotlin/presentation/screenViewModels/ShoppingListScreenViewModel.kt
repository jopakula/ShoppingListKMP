package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.FetchShoppingListResponseModel
import domain.useCases.FetchShoppingListByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListScreenViewModel(
    private val fetchShoppingListByIdUseCase: FetchShoppingListByIdUseCase
): ViewModel() {

    private val _shoppingListState = MutableStateFlow<RequestState<FetchShoppingListResponseModel>>(RequestState.Idle)
    val shoppingListState: StateFlow<RequestState<FetchShoppingListResponseModel>> = _shoppingListState

    fun loadShoppingList(listId: Int){
        viewModelScope.launch {
            _shoppingListState.value = RequestState.Loading
            runCatching {
                fetchShoppingListByIdUseCase.execute(listId = listId)
            }.onSuccess {response ->
                _shoppingListState.value = RequestState.Success(response)
            }.onFailure { exception ->
                _shoppingListState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}