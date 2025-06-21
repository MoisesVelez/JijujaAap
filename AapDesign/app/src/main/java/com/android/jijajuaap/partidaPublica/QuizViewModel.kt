package com.android.jijajuaap.partidaPublica


import androidx.compose.foundation.layout.Box
import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.test
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel  @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val db = Firebase.firestore
    private val _questions = MutableStateFlow<List<test>>(emptyList())
    val questions: StateFlow<List<test>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _correcta = MutableStateFlow(0)
    val correcto: StateFlow<Int> = _correcta

    fun loadQuestions(tema: String?) {
        db.collection(tema.toString()).get().addOnSuccessListener { result ->
            _questions.value = result.documents.mapNotNull {
                it.toObject(test::class.java)
            }.shuffled()
        }
    }

    fun answerQuestion(selectedIndex: Int): Boolean {
        val question = questions.value[currentIndex.value]
        if (selectedIndex == question.correctAnswerIndex) {
            _correcta.value += 1
            _score.value += 3;

        }

        if (_currentIndex.value <= questions.value.lastIndex) {
            _currentIndex.value += 1
        }else if(_currentIndex.value > questions.value.lastIndex){
            return true
        }
        return false
    }

    fun resetQuiz() {
        _currentIndex.value = 0
        _score.value = 0
        _correcta.value=0
    }






}
