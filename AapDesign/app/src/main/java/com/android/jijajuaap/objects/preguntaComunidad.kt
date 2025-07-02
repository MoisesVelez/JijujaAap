package com.android.jijajuaap.objects

import com.google.firebase.Timestamp


data class PreguntaComunidad (
    val titulo: String = "",
    val autor: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val preguntas: List<test> = emptyList()
){}