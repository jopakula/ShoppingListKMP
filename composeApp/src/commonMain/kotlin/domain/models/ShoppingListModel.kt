package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListModel(
    val id: Int,
    val name: String,
    val created: String
)