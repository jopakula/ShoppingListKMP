package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.ShoppingListModel
import domain.useCases.CreateShoppingListUseCase
import domain.useCases.FetchAllShoppingListsUseCase
import domain.useCases.RemoveShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val fetchAllShoppingListsUseCase: FetchAllShoppingListsUseCase,
    private val createShoppingListUseCase: CreateShoppingListUseCase,
    private val removeShoppingListUseCase: RemoveShoppingListUseCase,
) : ViewModel() {

    private val _shoppingListsState = MutableStateFlow<RequestState<List<ShoppingListModel>>>(RequestState.Idle)
    val shoppingListsState: StateFlow<RequestState<List<ShoppingListModel>>> = _shoppingListsState

    private val _createOperationState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val createOperationState: StateFlow<RequestState<Boolean>> = _createOperationState

    private val _removeOperationState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val removeOperationState: StateFlow<RequestState<Boolean>> = _removeOperationState



    fun loadShoppingLists(token: String) {
        viewModelScope.launch {
            runCatching {
                fetchAllShoppingListsUseCase.execute(key = token)
            }.onSuccess { response ->
                _shoppingListsState.value = RequestState.Success(response.shopList)
            }.onFailure { exception ->
                _shoppingListsState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun createShoppingList(token: String, name: String) {
        viewModelScope.launch {
            _createOperationState.value = RequestState.Loading
            runCatching {
                createShoppingListUseCase.execute(key = token, name = name)
            }.onSuccess { response ->
                if (response.success) {
                    _createOperationState.value = RequestState.Success(true)
                    loadShoppingLists(token)
                } else {
                    _createOperationState.value = RequestState.Error("Failed to create the shopping list")
                }
            }.onFailure { exception ->
                _createOperationState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun removeShoppingList(token: String, listId: Int) {
        viewModelScope.launch {
            _removeOperationState.value = RequestState.Loading
            runCatching {
                removeShoppingListUseCase.execute(listId)
            }.onSuccess { response ->
                if (response.success) {
                    _removeOperationState.value = RequestState.Success(response.newValue)
                    loadShoppingLists(token)
                } else {
                    _removeOperationState.value = RequestState.Error("Failed to remove the shopping list")
                }
            }.onFailure { exception ->
                _removeOperationState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}
