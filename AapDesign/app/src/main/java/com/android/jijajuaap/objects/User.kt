package com.android.jijajuaap.objects

data class User(
    val uid: String? = null,
    var name: String? = null,
    val email: String? = null,
    val avatarId: String? = "error_de_usuario",
    val totalPoints: Int? = null,
    val totalQuiz :Int? = null,
    val team: String?=null,
    val rango: String?=null
)
