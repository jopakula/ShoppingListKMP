package domain.useCases

import domain.Repository
import domain.models.CreateShoppingListResponse

class CreateShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(key: String, name: String): CreateShoppingListResponse {
        return repository.createShoppingList(key, name)
    }
}