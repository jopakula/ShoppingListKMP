package domain

import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.FetchShoppingListResponseModel
import domain.models.RemoveShoppingListResponseModel

interface Repository {
    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel

    suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel

    suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel

    suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponseModel

    suspend fun fetchShoppingListById(listId: Int): FetchShoppingListResponseModel

}