package com.android.jijajuaap.partidaPublica



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class gmaplayViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    var dificultad by mutableStateOf("")
        private set

    var pulsaciones by mutableStateOf(0)
        private set


        var preguntasFinal by mutableStateOf<test?>(null)
            private set



        var puntosFinal by mutableIntStateOf(0)
            private set


    var tipoTema: String by mutableStateOf("")
        private set

        fun preguntas(user: User?,string: String) {
            viewModelScope.launch {
                if (user?.tema.toString() == "Historia") {
                    preguntasFinal = authService.getTestData(string, "historia")
                } else if (user?.tema == "Naturaleza") {
                    preguntasFinal = authService.getTestData(string, "naturaleza")
                }
            }
        }



    fun puntos(user: User?) {
        val tema = user?.tema ?: return
        puntosFinal = when (tema) {
            "historia" -> user.puntosHistoria
            "naturaleza" -> user.puntosNaturaleza
            "deportes" -> user.puntosDeportes
            "filosofia" -> user.puntosFilosofia
            "culturaPopular" -> user.puntosCPop
            "literatura" -> user.puntosLiteratura
            else -> 0
        }
    }

    fun temas(user: User?): String {
        val tema = user?.tema
        tipoTema = when (tema) {
            "historia" -> "puntosHistoria"
            "naturaleza" -> "puntosNaturaleza"
            "deportes" -> "puntosDeportes"
            "filosofia" -> "puntosFilosofia"
            "culturaPopular" -> "puntosCPop"
            "literatura" -> "puntosLiteratura"
            else -> " "
        }
        return  tipoTema
    }



    fun actualizarDificultad(nueva: String) {
        dificultad = nueva
    }


    fun cantPulsado() {
        pulsaciones ++
    }

}





