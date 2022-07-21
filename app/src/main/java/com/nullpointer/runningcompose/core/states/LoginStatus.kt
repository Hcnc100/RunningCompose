package com.nullpointer.runningcompose.core.states

sealed class LoginStatus {
    object Unauthenticated : LoginStatus()
    object Authenticated : LoginStatus()
    object Authenticating : LoginStatus()
}