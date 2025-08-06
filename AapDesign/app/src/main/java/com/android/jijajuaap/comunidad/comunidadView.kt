package com.android.jijajuaap.comunidad

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.preguntaComunidad
import com.android.jijajuaap.objects.test
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class comunidadView @Inject constructor(
    private val authService: AuthService
) : ViewModel() {




    val listaTest = MutableStateFlow<List<preguntaComunidad>>(emptyList())
    var iD by mutableStateOf<String>("")
    var titulo by mutableStateOf<String>("")
    var creador by mutableStateOf<String>("")
    var preguntasCom by mutableStateOf<preguntaComunidad?>(null)
    var preguntaTest by mutableStateOf<test?>(null)
    var contador by mutableIntStateOf(0)
    var buenPunto by mutableIntStateOf(0)
    var incorrecto by mutableIntStateOf(0)
    var finalizador : Boolean by mutableStateOf(false)

    fun obtenerQuiz(){

        val db = FirebaseFirestore.getInstance()

        db.collection("comunidad")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(15)
            .get()
            .addOnSuccessListener { result ->
                val listaTests = result.toObjects(preguntaComunidad::class.java)
                listaTest.value = listaTests
                for (test in listaTests) {
                    Log.d("Firestore", "Título: ${test.titulo} - Fecha: ${test.timestamp.toDate()}")
                }


            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al recuperar tests", e)
            }


    }

    suspend fun buscador(quiz: String): List<preguntaComunidad> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val result = db.collection("comunidad")
                .whereEqualTo("titulo", quiz)
                .get()
                .await()

            result.mapNotNull { doc ->
                val pregunta = doc.toObject(preguntaComunidad::class.java)
                pregunta.id = doc.id
                pregunta
            }

        } catch (e: Exception) {
            Log.e("Firestore", "Error en búsqueda", e)
            emptyList()
        }
    }

    fun preguntasQuiz(id: String) {
        viewModelScope.launch {
            if(id != null){
                preguntasCom = authService.obtenerQuizCom(id)
            }else{
                preguntasCom = null
            }

        }
    }
    fun generadorPreguntas(preguntaComunidad: preguntaComunidad?, num: Int): test? {
        try {
            preguntaTest = preguntaComunidad?.preguntas[num]
        }catch(e: Exception){
            finalizador = true
            e
        }
        return preguntaTest
    }

    fun comprobador(num: Int,num2: Int){
        if(num == num2){
            contador += 1
            buenPunto +=5
        }else{
            incorrecto +=1
        }
    }

    fun reset(){
        contador=0
        buenPunto = 0
        incorrecto = 0
        finalizador=false
    }
    fun finalizador(){
        if(incorrecto == 3){
            finalizador = true
        }
    }






}