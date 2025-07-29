package com.android.jijajuaap.comunidad

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes

import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.android.jijajuaap.ui.theme.White

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlin.collections.get
import kotlin.collections.plusAssign

@Composable
fun QuizCom(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,comunidadView: comunidadView) {

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
        }
    }
    val user = userMenuViewModel.user
    LaunchedEffect(user) {
        comunidadView.reset()
        comunidadView.preguntasQuiz(comunidadView.iD)
    }

    var preguntas = comunidadView.preguntasCom
    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))
    val colorChosen = userMenuViewModel.colorUsuario(colorEscogido)
    var numCont by remember { mutableIntStateOf(0) }
    comunidadView.generadorPreguntas(preguntas, numCont)
    var preguntasTest = comunidadView.preguntaTest



    Scaffold(
        topBar = { barraTo(user, imag, navHostController, colorEscogido) },

        ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .background(fondo)
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(20.dp))
            // Text("${comunidadView.iD}")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Creador: ${comunidadView.creador}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        "Quiz: ${comunidadView.titulo}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

            }


            //  Text("Quiz: ${preguntas?.preguntas}")
            if(comunidadView.finalizador == true){
            Column(modifier = Modifier.fillMaxSize())
            {Text("hola ${comunidadView.contador}") }
        }
            Spacer(modifier = Modifier.size(15.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(preguntasTest?.pregunta ?: "", color = Color.Black)
            }
            Divider(
                color = White,
                thickness = 3.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.size(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth().size(400.dp).padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(preguntasTest?.opciones ?: emptyList()) {index, opcion ->

                    Card(modifier = Modifier
                        .clickable(onClick = {numCont +=1
                        comunidadView.comprobador(
                            index,
                            preguntasTest?.correctAnswerIndex ?: -1
                        )})
                        .fillMaxWidth()
                        .padding(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Text(opcion, color = Color.Black)
                    }

                }
            }
            Temporizado(10,
                colorChosen,
                preguntasTest?.correctAnswerIndex ?: -1,
                preguntas?.preguntas ?: emptyList())



            }


        }


    }




    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun barraTo(
        user: User?,
        imag: Int,
        navHostController: NavHostController,
        colorEscogido: Color
    ) {

        var colorLetras by remember { mutableStateOf(Color.Black) }
        if (colorEscogido != Color.DarkGray) {
            colorLetras = Color.Black
        } else {
            colorLetras = Color.White
        }
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(id = imag),
                    contentDescription = "Logo App",
                    modifier = Modifier.size(75.dp).padding(5.dp)
                        .clip(CircleShape)
                        .border(2.dp, color = colorEscogido, CircleShape)
                        .clickable(onClick = { navHostController.navigate(Routes.MenuUser.routes) })
                        .background(color = Color.White),

                    )
            },
            modifier = Modifier.height(125.dp),
            colors = TopAppBarDefaults.topAppBarColors(Color.White),
            actions = {
                Text(
                    user?.name.toString(), modifier =
                        Modifier.padding(25.dp), fontWeight = FontWeight.Bold, color = colorLetras
                )

                Text(
                    "Puntos totales: ${user?.totalPoints}", modifier =
                        Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = colorLetras
                )


            })

    }


@Composable
fun Temporizado(
    tiempo: Int,
    color: Color,
    currentIndex: Int,
    questions: List<test>,
   // onTimeOut: () -> Unit
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

      //  onTimeOut()
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





