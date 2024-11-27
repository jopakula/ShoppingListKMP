package data

import data.storage.NetworkStorage
import domain.Repository
import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponse
import domain.models.FetchAllShoppingListsResponse

class RepositoryImpl(
    private val networkStorage: NetworkStorage,
): Repository {
    override suspend fun createKey(): String {
        return networkStorage.createKey()
    }

    override suspend fun logIn(key: String): AuthResponseModel {
        return networkStorage.logIn(key = key)
    }

    override suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponse {
        return networkStorage.createShoppingList(key = key, name = name)
    }

    override suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponse {
        return networkStorage.fetchAllShoppingLists(key = key)
    }
}