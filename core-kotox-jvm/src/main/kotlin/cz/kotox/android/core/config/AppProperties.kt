package cz.kotox.android.core.config

interface AppProperties {
    val baseUrl: String
    val isDevEnvironment: Boolean
    val networkRequestTimeoutSec: Long
    val isDarkMode: Boolean
}