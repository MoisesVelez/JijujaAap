package com.android.jijajuaap.partidaPublica

import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.test
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadQuestions(tema: String?) {
        db.collection(tema.toString()).get().addOnSuccessListener { result ->
            _questions.value = result.documents.mapNotNull {
                it.toObject(test::class.java)
            }
        }
    }

    fun answerQuestion(selectedIndex: Int) {
        val question = questions.value[currentIndex.value]
        if (selectedIndex == question.correctAnswerIndex) {
            _score.value += 10
        }

        if (_currentIndex.value < questions.value.lastIndex) {
            _currentIndex.value += 1
        }
    }

    fun resetQuiz() {
        _currentIndex.value = 0
        _score.value = 0
    }
}
