package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddItemResponseModel(
    val success: Boolean,
    @SerialName("item_id")
    val itemId: Int
)
