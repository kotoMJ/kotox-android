package cz.kotox.common.network.config

interface AppNetworkingProperties {
    val baseUrl: String
    val networkRequestTimeoutSec: Long
}