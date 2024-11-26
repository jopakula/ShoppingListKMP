package data.storage.network

import data.storage.NetworkStorage
import domain.RequestState
import domain.models.AuthResponseModel

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
}