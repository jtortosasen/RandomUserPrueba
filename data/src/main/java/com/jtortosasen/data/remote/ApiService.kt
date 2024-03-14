package com.jtortosasen.data.remote

import com.jtortosasen.data.remote.responses.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    internal suspend fun fetchRandomUserList(page: Int, resultSize: Int, seed: String): Result =
        client.get(Routes.API) {
            parameter("page", page)
            parameter("results", resultSize)
            parameter("seed", seed)
        }.body()

    internal fun close() = client.close()
}