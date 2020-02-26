package capstone.network.client

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class TestRecipeService
{
    private val server = MockWebServer().apply {
        start()
    }

    init {
        server.enqueue(MockResponse().setBody(""))
    }


    @Test
    fun testBaseUrl()
    {
//        val service: RecipeService = NetworkModule(server.url("/")).provideRetrofit().create(RecipeService::class.java)
    }
}