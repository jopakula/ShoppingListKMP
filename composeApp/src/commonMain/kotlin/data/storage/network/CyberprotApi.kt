package data.storage.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


private const val BASE_URL_CYBERPROT = "https://cyberprot.ru/shopping/v2/CreateTestKey?"

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

    suspend fun createTestKey(): String {
        return client.get(urlString = BASE_URL_CYBERPROT).body()
    }
}
