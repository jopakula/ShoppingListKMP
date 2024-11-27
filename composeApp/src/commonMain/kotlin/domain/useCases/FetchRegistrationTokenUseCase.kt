package domain.useCases

import domain.Repository

class FetchRegistrationTokenUseCase(private val repository: Repository) {
    suspend fun execute(): String {
        return repository.createKey()
    }
}
