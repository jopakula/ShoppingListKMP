package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchShoppingListResponseModel (
    val success: Boolean,
    @SerialName("item_list")
    val itemList: List<ShoppingListItemModel>
)