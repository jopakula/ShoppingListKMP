package data.storage

import domain.models.AddItemResponseModel
import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.CrossOffResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.FetchShoppingListResponseModel
import domain.models.RemoveItemResponseModel
import domain.models.RemoveShoppingListResponseModel

interface NetworkStorage {

    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel

    suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel

    suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel

    suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponseModel

    suspend fun fetchShoppingListById(listId: Int): FetchShoppingListResponseModel

    suspend fun addItemToShoppingList(listId: Int, name: String, quantity: Int): AddItemResponseModel

    suspend fun removeItemFromShoppingList(listId: Int, itemId: Int): RemoveItemResponseModel

    suspend fun crossOffItem(itemId: Int): CrossOffResponseModel

}