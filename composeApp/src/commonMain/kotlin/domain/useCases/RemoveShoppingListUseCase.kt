package domain.useCases

import domain.Repository
import domain.models.RemoveShoppingListResponseModel

class RemoveShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int): RemoveShoppingListResponseModel {
        return repository.removeShoppingList(listId)
    }
}
