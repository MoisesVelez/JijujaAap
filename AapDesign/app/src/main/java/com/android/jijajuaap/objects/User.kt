package com.android.jijajuaap.objects

data class User(
    val uid: String? = null,
    var name: String? = null,
    val email: String? = null,
    val avatarId: String? = "error_de_usuario",
    val totalPoints: Int? = 0,
    val totalQuiz :Int? = 0,
    var team: String?="Sin equipo",
    val rango: String?= "Iniciado",
    val tema: String? = null,
    val puntosHistoria: Int=10,
    val puntosLiteratura: Int=0,
    val puntosFilosofia: Int=0,
    val puntosDeportes: Int=0,
    val puntosCPop: Int=0,
    val puntosNaturaleza: Int=0
)
