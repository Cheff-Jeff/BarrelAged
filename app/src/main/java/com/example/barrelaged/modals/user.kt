package com.example.barrelaged.modals

data class user(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val salt: String,
    val key: String?,
    val fingerPrint: String?
)
