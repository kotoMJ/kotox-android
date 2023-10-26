package cz.kotox.common.task.poc.data.impl.remote.api

import cz.kotox.android.core.config.AppProperties
import cz.kotox.core.network.config.AppNetworkingProperties
import cz.kotox.core.network.di.NetworkModuleProvider
import cz.kotox.core.test.MockResponseFileReader
import cz.kotox.common.task.poc.data.impl.di.DomainTaskModuleProvider
import cz.kotox.common.task.poc.data.impl.remote.dto.TaskDTO
import cz.kotox.common.task.poc.data.impl.remote.api.TaskApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.net.HttpURLConnection


class TestAppNetworkingProperties(
    override val networkRequestTimeoutSec: Long = 30,
    override val baseUrl: String = "https://api.npoint.io/",
) : AppNetworkingProperties

class TestAppProperties(
    override val isDevEnvironment: Boolean = true,
    override val isDarkMode: Boolean = false,
) : AppProperties

class TaskApiTest {

    private lateinit var mockWebServer: MockWebServer


    private lateinit var apiHelper: TaskApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }


    @Test
    fun apiTest() = runTest {

        val jsonContent = MockResponseFileReader("success_response.json").content
        assertNotNull(jsonContent)

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonContent)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()


        val appProperties = TestAppProperties()
        val appNetworkingProperties = TestAppNetworkingProperties()
        val networkProvider = NetworkModuleProvider()
        val okHttpClient = networkProvider.provideCommonOkHttpClient(
            appNetworkingProperties,
            networkProvider.provideLoggingInterceptor(appProperties)
        )
        val retrofit = networkProvider.provideCommonRetrofit(
            appNetworkingProperties,
            okHttpClient,
            networkProvider.provideMoshi()
        )

        val taskModuleProvider = DomainTaskModuleProvider()
        val taskApi = taskModuleProvider.provideTaskApi(retrofit)

        var actualResponse: List<TaskDTO>? = null


        launch {
            actualResponse = taskApi.getAllTasks()
        }

        Timber.d(">>>_ actualResponse: ${actualResponse}")
        //Log.d(">>_", "actualResponse: ${actualResponse}")

        var mockResponseBody: JSONObject? = null
        launch {
            mockResponseBody = mockResponse?.let { JSONObject(mockResponse) }
        }
        //Timber.d(">>>_ mockResponse: ${mockResponseBody}")
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}