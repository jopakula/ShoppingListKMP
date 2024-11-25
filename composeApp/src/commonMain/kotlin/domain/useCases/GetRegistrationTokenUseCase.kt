package domain.useCases

import domain.Repository

class GetRegistrationTokenUseCase(private val repository: Repository) {
    suspend fun execute(): String {
        return repository.getTestKey()
    }
}
