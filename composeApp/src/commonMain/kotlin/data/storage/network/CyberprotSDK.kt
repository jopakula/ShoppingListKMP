package data.storage.network

import domain.RequestState

class CyberprotSDK (private val api: CyberprotApi) {
    suspend fun fetchTestKey(): RequestState<String> {
        return try {
            val token = api.createTestKey()
            RequestState.Success(token)
        } catch (e: Exception) {
            RequestState.Error(e.message ?: "Unknown error")
        }
    }
}