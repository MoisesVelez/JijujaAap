package com.android.jijajuaap.partidaPublica

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.jijajuaap.objects.test
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.android.jijajuaap.menu.UserMenuViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun QuizScreen(viewModel: QuizViewModel, userMenuViewModel: UserMenuViewModel) {
    val questions by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid



    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
        }
    }
    val user = userMenuViewModel.user
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))

    LaunchedEffect(user?.tema) {
        viewModel.resetQuiz()
        viewModel.loadQuestions(user?.tema)
    }

        val question: test? = questions.getOrNull(currentIndex)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo),
            verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Pregunta ${currentIndex + 1}/${questions.size}", fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 25.dp))
            Text(question?.pregunta ?: "", fontSize = 20.sp)
            question?.opciones?.forEachIndexed { index, option ->
                Button(
                    onClick = { viewModel.answerQuestion(index) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option)
                }
            }
        }
    }



