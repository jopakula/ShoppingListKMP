package domain.useCases

import domain.Repository
import domain.models.RemoveItemResponseModel

class RemoveItemFromShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int, itemId: Int): RemoveItemResponseModel{
        return repository.removeItemFromShoppingList(listId = listId, itemId = itemId)
    }
}

