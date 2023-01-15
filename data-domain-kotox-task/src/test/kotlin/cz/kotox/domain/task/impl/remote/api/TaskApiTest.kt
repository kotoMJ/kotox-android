package cz.kotox.domain.task.impl.remote.api

import cz.kotox.core.test.MockResponseFileReader
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.net.HttpURLConnection

class TaskApiTest {

    private lateinit var mockWebServer: MockWebServer


//    private lateinit var apiHelper: ApiHelperImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }


    fun apiTest(){

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()

//        val  actualResponse = apiHelper.getEmployeeDetails().execute()
//        assertEquals(mockResponse?.let { `parse mocked JSON response`(it) }, actualResponse.body()?.status)

    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}