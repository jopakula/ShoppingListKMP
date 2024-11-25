package domain

interface Repository {
    suspend fun getTestKey(): String
}