package data.storage.network

import data.storage.NetworkStorage
import domain.RequestState

class NetworkStorageImpl(val sdk: CyberprotSDK): NetworkStorage {
    override suspend fun getTestKey(): String {
        val response = sdk.fetchTestKey()

        return when (response) {
            is RequestState.Success -> response.data
            is RequestState.Error -> throw Exception(response.message)
            else -> throw Exception("Unexpected state")
        }
    }
}