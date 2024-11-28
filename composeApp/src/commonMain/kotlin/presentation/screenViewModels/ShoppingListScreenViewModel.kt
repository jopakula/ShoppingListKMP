package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.FetchShoppingListResponseModel
import domain.useCases.AddItemToShoppingListUseCase
import domain.useCases.FetchShoppingListByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListScreenViewModel(
    private val fetchShoppingListByIdUseCase: FetchShoppingListByIdUseCase,
    private val addItemToShoppingListUseCase: AddItemToShoppingListUseCase,
): ViewModel() {

    private val _shoppingListState = MutableStateFlow<RequestState<FetchShoppingListResponseModel>>(RequestState.Idle)
    val shoppingListState: StateFlow<RequestState<FetchShoppingListResponseModel>> = _shoppingListState

    private val _addItemState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val addItemState: StateFlow<RequestState<Boolean>> = _addItemState

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

    fun addItemToShoppingList(listId: Int, name: String, quantity: Int) {
        viewModelScope.launch {
            _addItemState.value = RequestState.Loading
            kotlin.runCatching {
                addItemToShoppingListUseCase.execute(listId, name, quantity)
            }.onSuccess { response ->
                if (response.success){
                    _addItemState.value = RequestState.Success(true)
                    loadShoppingList(listId)
                } else {
                    _addItemState.value = RequestState.Error("Failed to add item")
                }
            }.onFailure { exception ->
                _addItemState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}