package domain

import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponse
import domain.models.FetchAllShoppingListsResponse

interface Repository {
    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel

    suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponse

    suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponse

}