package com.jtortosasen.randomuserapp.ui.models

import android.os.Parcelable
import com.jtortosasen.domain.models.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUi(
    val name: String,
    val email: String,
    val image: String,
    val genre: String,
    val registerDate: String,
    val phone: String,
    val address: String
) : Parcelable

fun User.toParcelize() = UserUi(
    name = this.name,
    email = this.email,
    image = this.image,
    genre = this.genre,
    registerDate = this.registerDate,
    phone = this.phone,
    address = this.address
)

fun UserUi.toDomain() = User(
    name = this.name,
    email = this.email,
    image = this.image,
    genre = this.genre,
    registerDate = this.registerDate,
    phone = this.phone,
    address = this.address
)