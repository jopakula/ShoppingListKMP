package presentation.screenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.RequestState
import domain.models.ShoppingListModel
import domain.useCases.CreateShoppingListUseCase
import domain.useCases.FetchAllShoppingListsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val fetchAllShoppingListsUseCase: FetchAllShoppingListsUseCase,
    private val createShoppingListUseCase: CreateShoppingListUseCase
) : ViewModel() {

    private val _shoppingListsState = MutableStateFlow<RequestState<List<ShoppingListModel>>>(RequestState.Idle)
    val shoppingListsState: StateFlow<RequestState<List<ShoppingListModel>>> = _shoppingListsState


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
            runCatching {
                createShoppingListUseCase.execute(key = token, name = name)
            }.onSuccess { response ->
                loadShoppingLists(token)
            }.onFailure { exception ->
                println("Error creating shopping list: ${exception.message}")
            }
        }
    }
}
