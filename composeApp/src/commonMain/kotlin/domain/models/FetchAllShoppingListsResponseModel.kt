package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchAllShoppingListsResponseModel(
    @SerialName("shop_list")
    val shopList: List<ShoppingListModel>,
    val success: Boolean
)