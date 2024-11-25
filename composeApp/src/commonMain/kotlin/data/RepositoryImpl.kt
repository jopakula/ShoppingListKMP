package data

import data.storage.NetworkStorage
import domain.Repository

class RepositoryImpl(
    private val networkStorage: NetworkStorage,
): Repository {
    override suspend fun getTestKey(): String {
        return networkStorage.getTestKey()
    }
}