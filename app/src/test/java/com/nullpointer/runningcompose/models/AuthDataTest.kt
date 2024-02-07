package com.nullpointer.runningcompose.models

import com.nullpointer.runningcompose.models.data.AuthData
import junit.framework.TestCase.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthDataTest {

    @Test
    fun `isAuth returns true when name is not empty and weight is not negative`() {
        val authData = AuthData(name = "John Doe", weight = 70F)
        assertTrue(authData.isAuth)
    }

    @Test
    fun `isAuth returns false when name is empty`() {
        val authData = AuthData(name = "", weight = 70F)
        assertFalse(authData.isAuth)
    }

    @Test
    fun `isAuth returns false when weight is negative`() {
        val authData = AuthData(name = "John Doe", weight = -1F)
        assertFalse(authData.isAuth)
    }

    @Test
    fun `isAuth returns false when name is empty and weight is negative`() {
        val authData = AuthData(name = "", weight = -1F)
        assertFalse(authData.isAuth)
    }
}