package data

import data.storage.NetworkStorage
import domain.Repository
import domain.models.AuthResponseModel

class RepositoryImpl(
    private val networkStorage: NetworkStorage,
): Repository {
    override suspend fun createKey(): String {
        return networkStorage.createKey()
    }

    override suspend fun logIn(key: String): AuthResponseModel {
        return networkStorage.logIn(key = key)
    }
}