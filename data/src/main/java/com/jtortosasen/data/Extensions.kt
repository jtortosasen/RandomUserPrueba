package com.jtortosasen.data

import com.jtortosasen.data.remote.responses.RandomUser
import com.jtortosasen.domain.models.Error
import com.jtortosasen.domain.models.User
import io.ktor.client.plugins.ResponseException
import java.io.IOException


internal fun RandomUser.toDomain(): User {
    return User(
        name = this.name,
        email = this.email,
        image = this.picture,
        genre = this.gender,
        registerDate = this.registered.toString(),
        phone = this.phone,
        address = this.location.toString()
    )
}

internal fun Exception.toError(): Error {
    return when (this) {
        is IOException -> Error.Connectivity
        is ResponseException -> Error.Server(this.response.status.value)
        else -> Error.Unknown(message ?: "Unknown Error")
    }
}