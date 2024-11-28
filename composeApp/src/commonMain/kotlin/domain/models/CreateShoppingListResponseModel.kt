package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateShoppingListResponseModel(
    val success: Boolean,
    @SerialName("list_id")
    val listId: Int,
)