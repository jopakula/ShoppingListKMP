package domain.useCases

import domain.Repository
import domain.models.FetchShoppingListResponseModel

class FetchShoppingListByIdUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int): FetchShoppingListResponseModel {
        return repository.fetchShoppingListById(listId = listId)
    }
}