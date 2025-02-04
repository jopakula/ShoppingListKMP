package data.storage.network

import domain.RequestState
import domain.models.AddItemResponseModel
import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.CrossOffResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.FetchShoppingListResponseModel
import domain.models.RemoveItemResponseModel
import domain.models.RemoveShoppingListResponseModel

class CyberprotSDK (private val api: CyberprotApi) {

    suspend fun createKey(): RequestState<String> {
        return try {
            val token = api.createKey()
            RequestState.Success(token)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun logIn(key: String): RequestState<AuthResponseModel> {
        return try {
            val response = api.authenticateWithKey(key = key)
            RequestState.Success(response)
        } catch (e: Exception){
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun createShoppingList(key: String, name: String): RequestState<CreateShoppingListResponseModel> {
        return try {
            val response = api.createShoppingList(key = key, name = name)
            RequestState.Success(response)
        } catch (e: Exception){
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun fetchAllShoppingLists(key: String): RequestState<FetchAllShoppingListsResponseModel> {
        return try {
            val response = api.fetchAllShoppingLists(key = key)
            RequestState.Success(response)
        } catch (e: Exception){
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun removeShoppingList(listId: Int): RequestState<RemoveShoppingListResponseModel> {
        return try {
            val response = api.removeShoppingList(listId = listId)
            RequestState.Success(response)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun fetchShoppingListById(listId: Int): RequestState<FetchShoppingListResponseModel> {
        return try {
            val response = api.fetchShoppingListById(listId = listId)
            RequestState.Success(response)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun  addItemToShoppingList(listId: Int, name: String, quantity: Int): RequestState<AddItemResponseModel> {
        return try {
            val response = api.addItemToShoppingList(listId = listId, name = name, quantity = quantity)
            RequestState.Success(response)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun removeItemFromShoppingList(listId: Int, itemId: Int): RequestState<RemoveItemResponseModel> {
        return try {
            val response = api.removeItemFromShoppingList(listId = listId, itemId = itemId)
            RequestState.Success(response)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun crossOffItem(itemId: Int): RequestState<CrossOffResponseModel> {
        return try {
            val response = api.crossOffItem(itemId = itemId)
            RequestState.Success(response)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

}