package com.alpharishi.passwordmanagerassignment

data class PasswordEntity(
    val id: Int = passwordCounter++,
    val accountName: String,
    val email: String,
    val password: String
)

var passwordCounter = 0