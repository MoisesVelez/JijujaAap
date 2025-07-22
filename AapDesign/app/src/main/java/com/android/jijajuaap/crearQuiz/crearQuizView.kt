package com.android.jijajuaap.crearQuiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.PreguntaComunidad
import com.android.jijajuaap.objects.test
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class crearQuizView @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    var titulO by mutableStateOf<String>("")
    var autOr by mutableStateOf<String>("")
    var listaPreguntas = MutableStateFlow<List<test>>(emptyList())

    fun añadirPregunta(test: test){
        listaPreguntas.value = listaPreguntas.value + test
    }


    fun subirQuiz() {
        val nuevoQuiz = PreguntaComunidad(
            titulo = titulO,
            autor = autOr,
            preguntas = listaPreguntas.value
        )

        FirebaseFirestore.getInstance()
            .collection("comunidad")
            .add(nuevoQuiz)
            .addOnSuccessListener {
                Log.d("crearQuizView", "Quiz subido con éxito")
            }
            .addOnFailureListener { e ->
                Log.e("crearQuizView", "Error al subir el quiz", e)
            }
    }
    fun borrarPreguntas(){
        listaPreguntas.value = emptyList()

    }


}