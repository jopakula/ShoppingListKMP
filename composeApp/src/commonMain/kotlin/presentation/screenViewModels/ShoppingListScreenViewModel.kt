package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.ShoppingListItemModel
import domain.useCases.AddItemToShoppingListUseCase
import domain.useCases.CrossOffItemUseCase
import domain.useCases.FetchShoppingListByIdUseCase
import domain.useCases.RemoveItemFromShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListScreenViewModel(
    private val fetchShoppingListByIdUseCase: FetchShoppingListByIdUseCase,
    private val addItemToShoppingListUseCase: AddItemToShoppingListUseCase,
    private val removeItemFromShoppingListUseCase: RemoveItemFromShoppingListUseCase,
    private val crossOffItemUseCase: CrossOffItemUseCase,
): ViewModel() {

    private val _shoppingListState = MutableStateFlow<RequestState<List<ShoppingListItemModel>>>(RequestState.Idle)
    val shoppingListState: StateFlow<RequestState<List<ShoppingListItemModel>>> = _shoppingListState

    private val _addOperationState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val addOperationState: StateFlow<RequestState<Boolean>> = _addOperationState

    private val _removeOperationState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val removeOperationState: StateFlow<RequestState<Boolean>> = _removeOperationState


    fun loadShoppingList(listId: Int){
        viewModelScope.launch {
            runCatching {
                fetchShoppingListByIdUseCase.execute(listId = listId)
            }.onSuccess {response ->
                _shoppingListState.value = RequestState.Success(response.itemList)
            }.onFailure { exception ->
                _shoppingListState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun addItemToShoppingList(listId: Int, name: String, quantity: Int) {
        viewModelScope.launch {
            _addOperationState.value = RequestState.Loading
            kotlin.runCatching {
                addItemToShoppingListUseCase.execute(listId = listId, name = name, quantity = quantity)
            }.onSuccess { response ->
                if (response.success){
                    _addOperationState.value = RequestState.Success(true)
                    loadShoppingList(listId)
                } else {
                    _addOperationState.value = RequestState.Error("Failed to add item")
                }
            }.onFailure { exception ->
                _addOperationState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun removeItemFromList(listId: Int, itemId: Int) {
        viewModelScope.launch {
            _removeOperationState.value = RequestState.Loading
            runCatching {
                removeItemFromShoppingListUseCase.execute(listId = listId, itemId = itemId)
            }.onSuccess { response ->
                if (response.success){
                    _removeOperationState.value = RequestState.Success(response.success)
                    loadShoppingList(listId = listId)
                } else {
                    _removeOperationState.value = RequestState.Error("Failed to remove item")
                }
            }.onFailure { exception ->
                _removeOperationState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun crossOffItem(itemId: Int, listId: Int,) {
        viewModelScope.launch {
            runCatching {
                crossOffItemUseCase.execute(itemId)
            }.onSuccess {
                loadShoppingList(listId)
            }.onFailure { exception ->
                _shoppingListState.value = RequestState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}