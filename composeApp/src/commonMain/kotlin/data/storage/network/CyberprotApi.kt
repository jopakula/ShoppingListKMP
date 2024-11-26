package data.storage.network

import domain.models.AuthResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


private const val BASE_URL_CYBERPROT = "https://cyberprot.ru/shopping/v2"

class CyberprotApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun createKey(): String {
        val url = "$BASE_URL_CYBERPROT/CreateTestKey?"
        return client.get(urlString = url).body()
    }


    suspend fun authenticateWithKey(key: String): AuthResponseModel {
        val url = "$BASE_URL_CYBERPROT/Authentication?key=$key"
        return client.get(url).body()
    }
}
