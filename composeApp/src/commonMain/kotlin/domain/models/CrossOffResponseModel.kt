package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrossOffResponseModel(
    val success: Boolean,
    @SerialName("rows_affected")
    val rowsAffected: Int
)
