package cz.kotox.common.test

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {

    val content: String

    init {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
        val reader = InputStreamReader(inputStream)
        content = reader.readText()
        reader.close()
    }
}