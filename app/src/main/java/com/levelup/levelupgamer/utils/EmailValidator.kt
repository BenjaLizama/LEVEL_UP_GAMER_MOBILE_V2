package com.levelup.levelupgamer.utils

import java.util.regex.Pattern

object EmailValidator {
    private val EMAIL_REGEX = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    )
    fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX.matcher(email).matches()
    }
}