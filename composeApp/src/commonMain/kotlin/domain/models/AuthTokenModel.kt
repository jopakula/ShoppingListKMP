package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenModel(
    val token: String
)
