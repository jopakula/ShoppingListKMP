package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveShoppingListResponse(
    val success: Boolean,
    @SerialName("new_value")
    val newValue: Boolean
)
