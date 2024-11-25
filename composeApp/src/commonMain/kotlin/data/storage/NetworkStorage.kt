package data.storage

interface NetworkStorage {

    suspend fun getTestKey(): String

}