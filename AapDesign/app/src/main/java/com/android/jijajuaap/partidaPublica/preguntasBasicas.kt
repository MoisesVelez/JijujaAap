package com.android.jijajuaap.partidaPublica

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    userMenuViewModel: UserMenuViewModel,
    navHostController: NavHostController,
    gameRoad: gmaplayViewModel
) {
    val questions by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val correctas by viewModel.correcto.collectAsState()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    var comprobante by remember { mutableStateOf(false) }


    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)

        }
    }
    val user = userMenuViewModel.user
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val colorChosen = userMenuViewModel.colorUsuario(colorEscogido)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))
    var puntosH = gameRoad.puntosFinal
    var pulsado = gameRoad.pulsaciones
    var temaPuntos = gameRoad.temas(user)
    LaunchedEffect(user?.tema) {
        viewModel.resetQuiz()
        viewModel.loadQuestions(user?.tema)

    }

    val question: test? = questions.getOrNull(currentIndex)
    val dificultad = gameRoad.dificultad

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BLANCOeSP),
            verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if(currentIndex == questions.size) {
            var total = puntosH + score
            userMenuViewModel.updatePuntos(temaPuntos,total)
            Column(
                modifier = Modifier.fillMaxSize().background(BLANCOeSP),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text("Terminado Quiz de ${user?.tema}", fontSize = 25.sp,color = Color.Black, fontWeight = FontWeight.Bold)
                Card(
                    modifier = Modifier
                        .height(350.dp)
                        .padding(20.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = colorEscogido)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().background(White).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {


                            ProfileInfoRow(R.drawable.puntuacion_mas_alta, label = "    Preguntas correctas: ", value =
                            "$correctas/ ${questions.size}"
                        )
                        ProfileInfoRow(R.drawable.puntuacion_mas_alta, label = "    Puntuación: ", value = "+ $score")
                        ProfileInfoRow(R.drawable.puntuacion_mas_alta, label = "    Puntuación total: ", value = "$total ")
                        ProfileInfoRow(R.drawable.puntuacion_mas_alta, label = "    Dificultad: ", value = dificultad)

                    }
                }
                Spacer(modifier = Modifier.size(25.dp))

                Button(onClick = {navHostController.popBackStack()
                    pulsado==0
                    navHostController.navigate(Routes.menuRoadMap.routes)
                    },
                    colors = ButtonDefaults.buttonColors(colorEscogido),
                    shape = RoundedCornerShape(12.dp)) {
                    Text("Volver", color =Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
            Spacer(modifier = Modifier.size(30.dp))
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Pregunta ${currentIndex + 1}/${questions.size}     Puntos: ${score}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 25.dp)
                )
            }
            Card(
                modifier = Modifier
                    .height(75.dp)
                    .padding(6.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        question?.pregunta ?: "", fontSize = 20.sp, textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(), color = Color.Black
                    )
                }
            }
            Card(
                modifier = Modifier
                    .height(350.dp)
                    .padding(6.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().background(White).padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    question?.opciones?.forEachIndexed { index, option ->
                        Button(
                            onClick = { comprobante = viewModel.answerQuestion(index)
                                      gameRoad.cantPulsado()},
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            colors = ButtonDefaults.buttonColors(colorEscogido),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(option, color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Temporizador(
                10,
                colorChosen,
                currentIndex,
                questions,
                onTimeOut = { viewModel.answerQuestion(-1) })


        }
    }
@Composable
fun Temporizador(
    tiempo: Int,
    color: Color,
    currentIndex: Int,
    questions: List<test>,
    onTimeOut: () -> Unit
) {
    var tiempoRestante by remember { mutableStateOf(tiempo) }
    val progreso = remember { Animatable(0f) }

    LaunchedEffect(currentIndex,questions) {
        if (questions.isEmpty()  || currentIndex >= questions.size) return@LaunchedEffect

        tiempoRestante = tiempo
        progreso.snapTo(0f)

        for (i in tiempo downTo 1) {
            delay(1000L)
            tiempoRestante--
            progreso.animateTo((tiempo - tiempoRestante).toFloat() / tiempo)
        }

        onTimeOut()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tiempo restante: $tiempoRestante s", color = Color.Black)

        LinearProgressIndicator(
            progress = progreso.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color.LightGray
        )
    }
}



@Composable
fun ProfileInfoRow(icono: Int, label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(painterResource(id = icono), contentDescription = "", modifier = Modifier.size(25.dp))
                Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Text(value, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}
