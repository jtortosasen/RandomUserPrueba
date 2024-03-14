package com.jtortosasen.data.console

import arrow.core.Either
import co.touchlab.kermit.Logger
import com.jtortosasen.domain.models.Error

internal fun showResponse(response: Either<Error, Any>){
    response.fold(
        { error -> printError(error) },
        { result ->  Logger.i { result.toString() } }
    )
}

internal fun printError(error: Error){
    when (error) {
        is Error.Server -> {
            Logger.i { "Server error with code: ${error.code}" }
        }
        is Error.Connectivity -> {
            Logger.i { "Connectivity error" }
        }
        is Error.Unknown -> {
            Logger.i { "Unknown error: ${error.message}" }
        }
    }
}