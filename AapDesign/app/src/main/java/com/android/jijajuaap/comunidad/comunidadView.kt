package com.android.jijajuaap.comunidad

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.preguntaComunidad
import com.android.jijajuaap.objects.test
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt

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


//****************************************************************

    private val _contVidas = MutableStateFlow(0)
    val contVida: StateFlow<Int> = _contVidas

    private val _escudos = MutableStateFlow(0)
    val escudos: StateFlow<Int> = _escudos

    private val _segundoLatido = MutableStateFlow(false)
    val segundoLatid: StateFlow<Boolean> = _segundoLatido.asStateFlow()

    private val _escudo = MutableStateFlow(false)
    val escudo: StateFlow<Boolean> = _escudo.asStateFlow()

    private val _DRecompsa = MutableStateFlow(false)
    val DRecompsa: StateFlow<Boolean> = _DRecompsa.asStateFlow()

    private val _Memoria = MutableStateFlow(false)
    val Memoria: StateFlow<Boolean> = _Memoria.asStateFlow()

    private val _Monedero = MutableStateFlow(false)
    val Monedero: StateFlow<Boolean> = _Monedero.asStateFlow()

    private val _PHumilde = MutableStateFlow(false)
    val PHumilde: StateFlow<Boolean> = _PHumilde.asStateFlow()

    private val _CTotal = MutableStateFlow(false)
    val CTotal: StateFlow<Boolean> = _CTotal.asStateFlow()

    private val _ERapido = MutableStateFlow(false)
    val ERapido: StateFlow<Boolean> = _ERapido.asStateFlow()

    private val _SPregunta = MutableStateFlow(false)
    val SPregunta: StateFlow<Boolean> = _SPregunta.asStateFlow()

    private val _PRapida = MutableStateFlow(false)
    val PRapida: StateFlow<Boolean> = _PRapida.asStateFlow()





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

            if(PHumilde.value){
                buenPunto +=  1
            }


            if(DRecompsa.value && Memoria.value){
                var puntosD = ceil(5 * 0.25).roundToInt()
                var puntosF = puntosD + 5
                var puntosMe = (puntosF * 2)
                buenPunto += puntosMe;
            }else if(DRecompsa.value && Memoria.value == false){
                var puntosD = 5 * 2
                buenPunto += puntosD;
            }else if(Memoria.value && DRecompsa.value==false){
                var puntosD = ceil(5 * 0.25).roundToInt()
                var puntosF= 5 + puntosD
                buenPunto += puntosF;
            }
            else{
                buenPunto += 5
            }

        }else if(segundoLatid.value ) {
            contador += 1
            _segundoLatido.value = false
            _contVidas.value += 1


            if(DRecompsa.value && Memoria.value){
                var puntosD = ceil(5 * 0.25).roundToInt()
                var puntosF = puntosD + 5
                var puntosMe = (puntosF * 2)
                buenPunto += puntosMe;
            }else if(DRecompsa.value && Memoria.value == false){
                var puntosD = 5 * 2
                buenPunto += puntosD;
            }else if(Memoria.value && DRecompsa.value==false){
                var puntosD = ceil(5 * 0.25).roundToInt()
                var puntosF= 5 + puntosD
                buenPunto += puntosF;
            }
            else{
                buenPunto += 5
            }



        } else if(escudo.value) {
            _escudos.value += 1
            _escudo.value = false


        }else{
            incorrecto +=1
        }
    }

    fun reset(){
        contador=0
        buenPunto = 0
        incorrecto = 0
        finalizador=false

        _contVidas.value = 0
        _escudos.value = 0
        _DRecompsa.value=false
        _segundoLatido.value=false
        _escudo.value=false
        _Memoria.value=false
        _Monedero.value = false
        _PHumilde.value = false
        _CTotal.value = false
        _ERapido.value = false
        _PRapida.value = false
    }


    fun saltoFalse() {
        _SPregunta.value = false
    }

    fun pistaFalse() {
        _PRapida.value = false
    }



    fun finalizador(){
        if(incorrecto == 3){
            finalizador = true
        }
    }

    fun fueraTiempo(){
        incorrecto += 1
    }



    fun comprobarPasivas(user: User?){

        if (user?.mochila?.any { it.nombre == "Segundo Latido" } == true) {
            _segundoLatido.value=true
        }
        if (user?.mochila?.any { it.nombre == "Escudo de Error" } == true) {
            _escudo.value=true
        }
        if (user?.mochila?.any { it.nombre == "Doble Recompensa" } == true) {
            _DRecompsa.value=true
        }
        if (user?.mochila?.any { it.nombre == "Memoria de Acero" } == true) {
            _Memoria.value=true
        }
        if (user?.mochila?.any { it.nombre == "Monedero Inteligente" } == true) {
            _Monedero.value=true
        }
        if (user?.mochila?.any { it.nombre == "Punto Humilde" } == true) {
            _PHumilde.value=true
        }
        if (user?.mochila?.any { it.nombre == "Concentración Total" } == true) {
            _CTotal.value=true
        }
        if (user?.mochila?.any { it.nombre == "Estudiante Rápido" } == true) {
            _ERapido.value=true
        }
        if (user?.mochila?.any { it.nombre == "Salto de Pregunta" } == true) {
            _SPregunta.value=true
        }
        if (user?.mochila?.any { it.nombre == "Pista Rápida" } == true) {
            _PRapida.value=true
        }


    }

    fun sumadorScore(){
        buenPunto += 5
        _Monedero.value = false
    }






}