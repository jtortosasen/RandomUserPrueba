package com.jtortosasen.data.remote.responses

import com.jtortosasen.data.remote.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
internal data class RandomUser(
    @Serializable(with = UserFullNameSerializer::class)
    val name: String,
    val email: String,
    @Serializable(with = UserPictureSerializer::class)
    val picture: String,
    val gender: String,
    @Serializable(with = UserRegisteredDateSerializer::class)
    val registered: LocalDateTime,
    val phone: String,
    @Serializable(with = UserLocationSerializer::class)
    val location: Pair<Double, Double>
)