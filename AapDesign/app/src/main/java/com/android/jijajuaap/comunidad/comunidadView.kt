package com.android.jijajuaap.comunidad

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
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class comunidadView @Inject constructor(
    private val authService: AuthService
) : ViewModel() {




    val listaTest = MutableStateFlow<List<PreguntaComunidad>>(emptyList())
    var iD by mutableStateOf<String>("")
    var titulo by mutableStateOf<String>("")
    var creador by mutableStateOf<String>("")

    fun obtenerQuiz(){

        val db = FirebaseFirestore.getInstance()

        db.collection("comunidad")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(15)
            .get()
            .addOnSuccessListener { result ->
                val listaTests = result.toObjects(PreguntaComunidad::class.java)
                listaTest.value = listaTests
                for (test in listaTests) {
                    Log.d("Firestore", "Título: ${test.titulo} - Fecha: ${test.timestamp.toDate()}")
                }


            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al recuperar tests", e)
            }


    }

    suspend fun buscador(quiz: String): List<PreguntaComunidad> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val result = db.collection("comunidad")
                .whereEqualTo("titulo", quiz)
                .get()
                .await()

            result.mapNotNull { doc ->
                val pregunta = doc.toObject(PreguntaComunidad::class.java)
                pregunta.id = doc.id
                pregunta
            }

        } catch (e: Exception) {
            Log.e("Firestore", "Error en búsqueda", e)
            emptyList()
        }
    }




}