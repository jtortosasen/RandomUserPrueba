package com.jtortosasen.domain.models


sealed class Error {
    class Server(val code: Int) : Error()
    data object Connectivity : Error()
    class Unknown(val message: String) : Error()
}