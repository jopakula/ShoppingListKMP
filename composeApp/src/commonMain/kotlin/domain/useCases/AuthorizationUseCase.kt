package domain.useCases

import domain.Repository
import domain.models.AuthResponseModel

class AuthorizationUseCase(private val repository: Repository) {
    suspend fun execute(key: String): AuthResponseModel {
        return repository.logIn(key = key)
    }
}