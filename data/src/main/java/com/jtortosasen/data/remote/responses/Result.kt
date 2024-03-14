package com.jtortosasen.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class Result(
    val results: List<RandomUser>,
    val info: Info
)

@Serializable
internal data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)