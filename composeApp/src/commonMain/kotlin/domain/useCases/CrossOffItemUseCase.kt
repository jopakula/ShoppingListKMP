package domain.useCases

import domain.Repository
import domain.models.CrossOffResponseModel

class CrossOffItemUseCase(private val repository: Repository) {
    suspend fun execute(itemId: Int): CrossOffResponseModel{
        return repository.crossOffItem(itemId = itemId)
    }
}
