package com.nullpointer.runningcompose.models


@kotlinx.serialization.Serializable
data class AuthData(
    val name: String = "",
    val weight: Float = -1F,
    val photo: String = ""
) {
    val isAuth: Boolean = name.isNotEmpty() && weight != -1F
}