package domain.useCases

import domain.Repository
import domain.models.CreateShoppingListResponseModel

class CreateShoppingListUseCase(private val repository: Repository) {
    suspend fun execute(key: String, name: String): CreateShoppingListResponseModel {
        return repository.createShoppingList(key, name)
    }
}