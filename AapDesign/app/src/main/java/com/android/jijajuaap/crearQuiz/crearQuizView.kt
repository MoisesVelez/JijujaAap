package com.android.jijajuaap.crearQuiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService

import com.android.jijajuaap.objects.preguntaComunidad
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
    var iD by mutableStateOf<String>("")

    fun añadirPregunta(test: test){
        listaPreguntas.value = listaPreguntas.value + test
    }


    fun subirQuiz() {
        val db = FirebaseFirestore.getInstance()
        val idGenerado = db.collection("comunidad").document().id
        iD = idGenerado

        val nuevoQuiz = preguntaComunidad(
            id = idGenerado,
            titulo = titulO,
            autor = autOr,
            preguntas = listaPreguntas.value
        )

        db.collection("comunidad").document(idGenerado)
            .set(nuevoQuiz)
            .addOnSuccessListener {
                Log.d("crearQuizView", "Quiz subido con éxito. ID: $idGenerado")
            }
            .addOnFailureListener { e ->
                Log.e("crearQuizView", "Error al subir el quiz", e)
            }
    }

    fun borrarPreguntas(){
        listaPreguntas.value = emptyList()

    }


}