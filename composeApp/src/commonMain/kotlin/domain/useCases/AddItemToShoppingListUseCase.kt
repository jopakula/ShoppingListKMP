package domain.useCases

import domain.Repository
import domain.models.AddItemResponseModel

class AddItemToShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int, name: String, quantity: Int): AddItemResponseModel {
        return repository.addItemToShoppingList(listId, name, quantity)
    }
}
