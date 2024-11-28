package domain.useCases

import domain.Repository
import domain.models.RemoveShoppingListResponse

class RemoveShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int): RemoveShoppingListResponse {
        return repository.removeShoppingList(listId)
    }
}
