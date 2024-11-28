package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItemModel(
    val created: String,
    val name: String,
    val id: Int,
    @SerialName("is_crossed")
    val isCrossed: Boolean
)
