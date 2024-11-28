package domain.useCases

import domain.Repository
import domain.models.FetchAllShoppingListsResponseModel

class FetchAllShoppingListsUseCase(private val repository: Repository) {
    suspend fun execute(key: String): FetchAllShoppingListsResponseModel {
       return repository.fetchAllShoppingLists(key)
    }
}