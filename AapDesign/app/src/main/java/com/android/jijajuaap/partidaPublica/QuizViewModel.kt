package com.android.jijajuaap.partidaPublica


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int
import kotlin.math.ceil
import kotlin.math.roundToInt

@HiltViewModel
class QuizViewModel  @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val db = Firebase.firestore
    private val _questions = MutableStateFlow<List<test>>(emptyList())
    val questions: StateFlow<List<test>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private var _score = MutableStateFlow(0)
    var score: StateFlow<Int> = _score

    private val _correcta = MutableStateFlow(0)
    val correcto: StateFlow<Int> = _correcta

    private val _incorrecta = MutableStateFlow(0)
    val incorrecto: StateFlow<Int> = _incorrecta




    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _contVidas = MutableStateFlow(0)
    val contVidas: StateFlow<Int> = _contVidas

    private val _escudos = MutableStateFlow(0)
    val escudos: StateFlow<Int> = _escudos

    private val _segundoLatido = MutableStateFlow(false)
    val segundoLatido: StateFlow<Boolean> = _segundoLatido.asStateFlow()

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


    fun loadQuestions(tema: String?,tema2: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {


                db.collection(tema.toString() + tema2.toString()).get()
                    .addOnSuccessListener { result ->
                        _questions.value = result.documents.mapNotNull {
                            it.toObject(test::class.java)
                        }.shuffled()
                            .take(10)
                    }
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

        fun answerQuestion(selectedIndex: Int, puntos: Int): Boolean {
            val question = questions.value[currentIndex.value]
            if (selectedIndex == question.correctAnswerIndex) {
                _correcta.value += 1

                if(PHumilde.value){
                    _score.value +=  1
                }


                if(DRecompsa.value && Memoria.value){
                   var puntosD = ceil(puntos * 0.25).roundToInt()
                    var puntosF = puntosD + puntos
                    var puntosMe = (puntosF * 2)
                    _score.value += puntosMe;
                }else if(DRecompsa.value && Memoria.value == false){
                    var puntosD = puntos * 2
                    _score.value += puntosD;
                }else if(Memoria.value && DRecompsa.value==false){
                    var puntosD = ceil(puntos * 0.25).roundToInt()
                    var puntosF= puntos + puntosD
                    _score.value += puntosF;
                }
                else{
                    _score.value += puntos
                }



            }

           else if(segundoLatido.value ){
                _correcta.value += 1
                _segundoLatido.value=false
                _contVidas.value += 1

                if(DRecompsa.value && Memoria.value){
                    var puntosD = ceil(puntos * 0.25).roundToInt()
                    var puntosF = puntosD + puntos
                    var puntosMe = (puntosF * 2)
                    _score.value += puntosMe;
                }else if(DRecompsa.value && Memoria.value == false){
                    var puntosD = puntos * 2
                    _score.value += puntosD;
                }else if(Memoria.value && DRecompsa.value==false){
                    var puntosD = ceil(puntos * 0.25).roundToInt()
                    var puntosF= puntos + puntosD
                    _score.value += puntosF;
                }
                else{
                    _score.value += puntos
                }



            } else if(escudo.value) {
                _escudos.value += 1
                _escudo.value = false


            } else{
                _incorrecta.value += 1
            }
//--------------------------------------------
            if (_currentIndex.value <= questions.value.lastIndex) {
                _currentIndex.value += 1
            } else if (_currentIndex.value > questions.value.lastIndex) {
                return true
            }
            return false
        }

        fun resetQuiz() {
            _currentIndex.value = 0
            _score.value = 0
            _correcta.value = 0
            _incorrecta.value = 0
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


    //---------------------------PASIVAS


    fun comprobadorPasivas(user: User?){

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
        _score.value += 5
        _Monedero.value = false
    }






}
