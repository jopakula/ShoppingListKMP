package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateShoppingListResponse(
    val success: Boolean,
    val listId: Int
)