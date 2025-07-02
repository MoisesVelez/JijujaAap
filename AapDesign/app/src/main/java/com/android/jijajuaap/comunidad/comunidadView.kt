package com.android.jijajuaap.comunidad

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.PreguntaComunidad
import com.android.jijajuaap.objects.test
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class comunidadView @Inject constructor(
    private val authService: AuthService
) : ViewModel() {




    val listaTest = MutableStateFlow<List<PreguntaComunidad>>(emptyList())

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



}