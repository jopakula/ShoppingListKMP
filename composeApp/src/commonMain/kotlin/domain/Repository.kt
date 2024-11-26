package domain

import domain.models.AuthResponseModel

interface Repository {
    suspend fun createKey(): String

    suspend fun logIn(key: String): AuthResponseModel
}