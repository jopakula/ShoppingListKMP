package domain.useCases

import domain.Repository
import domain.models.FetchAllShoppingListsResponse

class FetchAllShoppingListsUseCase(private val repository: Repository) {
    suspend fun execute(key: String): FetchAllShoppingListsResponse {
       return repository.fetchAllShoppingLists(key)
    }
}