package data.storage.network

import domain.RequestState
import domain.models.AuthResponseModel

class CyberprotSDK (private val api: CyberprotApi) {

    suspend fun createKey(): RequestState<String> {
        return try {
            val token = api.createKey()
            RequestState.Success(token)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun logIn(key: String): RequestState<AuthResponseModel>{
        return try {
            val response = api.authenticateWithKey(key = key)
            RequestState.Success(response)
        } catch (e: Exception){
            RequestState.Error(e.message ?: "Unknown error")
        }
    }

}