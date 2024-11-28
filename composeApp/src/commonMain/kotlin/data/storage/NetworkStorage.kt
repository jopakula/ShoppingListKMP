package data.storage

import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.RemoveShoppingListResponse

interface NetworkStorage {

    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel

    suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel

    suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel

    suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponse

}