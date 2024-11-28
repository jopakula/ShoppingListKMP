package data

import data.storage.NetworkStorage
import domain.Repository
import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.FetchShoppingListResponseModel
import domain.models.RemoveShoppingListResponseModel

class RepositoryImpl(
    private val networkStorage: NetworkStorage,
): Repository {
    override suspend fun createKey(): String {
        return networkStorage.createKey()
    }

    override suspend fun logIn(key: String): AuthResponseModel {
        return networkStorage.logIn(key = key)
    }

    override suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel {
        return networkStorage.createShoppingList(key = key, name = name)
    }

    override suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel {
        return networkStorage.fetchAllShoppingLists(key = key)
    }

    override suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponseModel {
        return networkStorage.removeShoppingList(listId = listId)
    }

    override suspend fun fetchShoppingListById(listId: Int): FetchShoppingListResponseModel {
        return networkStorage.fetchShoppingListById(listId = listId)
    }
}