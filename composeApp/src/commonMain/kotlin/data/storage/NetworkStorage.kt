package data.storage

import domain.models.AuthResponseModel

interface NetworkStorage {

    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel

}