package cz.kotox.core.network.config

interface AppNetworkingProperties {
    val baseUrl: String
    val networkRequestTimeoutSec: Long
}