package data.storage.network

import data.storage.NetworkStorage
import domain.RequestState
import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.FetchShoppingListResponseModel
import domain.models.RemoveShoppingListResponseModel

class NetworkStorageImpl(val sdk: CyberprotSDK): NetworkStorage {
    override suspend fun createKey(): String {
        val response = sdk.createKey()

        return when (response) {
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }

    override suspend fun logIn(key: String): AuthResponseModel {
        val response = sdk.logIn(key = key)

        return when (response){
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }

    override suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel {
        val response = sdk.createShoppingList(key = key, name = name)

        return when (response){
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }

    override suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel {
        val response = sdk.fetchAllShoppingLists(key = key)

        return when (response){
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }

    override suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponseModel {
        val response = sdk.removeShoppingList(listId)
        return when (response) {
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }

    override suspend fun fetchShoppingListById(listId: Int): FetchShoppingListResponseModel {
        val response = sdk.fetchShoppingListById(listId)
        return when (response) {
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }
}