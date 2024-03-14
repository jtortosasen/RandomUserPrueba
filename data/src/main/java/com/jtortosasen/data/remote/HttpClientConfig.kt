package com.jtortosasen.data.remote

import com.jtortosasen.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
internal val httpClient = HttpClient(OkHttp) {
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 5)
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            explicitNulls = false
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                co.touchlab.kermit.Logger.d { message }
            }
        }
        level = LogLevel.ALL
    }
    install(DefaultRequest) {
        url(BuildConfig.BASE_URL)
        url { parameters.append("nat", "es") }
    }
}