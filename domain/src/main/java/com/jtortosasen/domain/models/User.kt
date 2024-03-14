package com.jtortosasen.domain.models

data class User(
    val name: String,
    val email: String,
    val image: String,
    val genre: String,
    val registerDate: String,
    val phone: String,
    val address: String
)