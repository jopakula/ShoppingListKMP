package data.storage.network

import domain.models.AuthResponseModel
import domain.models.CreateShoppingListResponseModel
import domain.models.FetchAllShoppingListsResponseModel
import domain.models.RemoveShoppingListResponse
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
                    isLenient = true
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
        return client.get(urlString = url).body()
    }

    suspend fun createShoppingList(key: String, name: String): CreateShoppingListResponseModel{
        val url = "$BASE_URL_CYBERPROT/CreateShoppingList"
        return client.get(url) {
            url {
                parameters.append("key", key)
                parameters.append("name", name)
            }
        }.body()
    }

    suspend fun fetchAllShoppingLists(key: String): FetchAllShoppingListsResponseModel {
        val url = "$BASE_URL_CYBERPROT/GetAllMyShopLists?key=$key"
        return client.get(urlString = url).body()
    }

    suspend fun removeShoppingList(listId: Int): RemoveShoppingListResponse {
        val url = "$BASE_URL_CYBERPROT/RemoveShoppingList?list_id=$listId"
        return client.get(url).body()
    }
}
