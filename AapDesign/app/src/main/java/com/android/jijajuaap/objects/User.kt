package com.android.jijajuaap.objects

data class User(
    val uid: String = "",
    val names: String = "",
    val email: String = "",
    val avatarId: String? = null,
    val totalPoints: Int = 0,
    val totalQuiz :Int = 0,
    val betterPoint: Int = 0,
    val registerDate: Long = System.currentTimeMillis()
)
