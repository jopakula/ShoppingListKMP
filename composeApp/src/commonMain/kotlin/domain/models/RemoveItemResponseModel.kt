package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RemoveItemResponseModel(
    val success: Boolean
)
