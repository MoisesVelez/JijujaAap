package com.android.jijajuaap.partidaPublica

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.pintor.pintorView
import com.android.jijajuaap.presentation.login.LoginScreen
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    userMenuViewModel: UserMenuViewModel,
    navHostController: NavHostController,
    gameRoad: gmaplayViewModel,
    pintorView: pintorView,

) {
    val questions by viewModel.questions.collectAsState()
    var currentIndex: Int = viewModel.currentIndex.value
    val score by viewModel.score.collectAsState()
    val correctas by viewModel.correcto.collectAsState()
    val incorrectas by viewModel.incorrecto.collectAsState()
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
    val isLoading by viewModel.isLoading.collectAsState()



    val dificultad = gameRoad.dificultad
    var eleccion by remember { mutableStateOf("") }
    eleccion = eleccionTest(dificultad)
    Log.e("eleccion",eleccion)
    LaunchedEffect(user?.tema) {
        viewModel.resetQuiz()
        viewModel.loadQuestions(user?.tema,eleccion)
        viewModel.comprobadorPasivas(user)

    }
    var segundoLatido = viewModel.segundoLatido
    var escudo = viewModel.escudo

    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }

    val question: test? = questions.getOrNull(currentIndex)


    var puntuacion by remember { mutableIntStateOf(0) }

    fun dificultadPuntos():Int{
        if(dificultad.equals("Aprendiz")){
            puntuacion = 3
            return puntuacion
        }else if(dificultad.equals("Intermedio")){
            puntuacion = 4
            return puntuacion
        }else if(dificultad.equals("Avanzado")) {
            puntuacion = 5
            return puntuacion
        }else if(dificultad.equals("Profesional")) {
            puntuacion = 7
            return puntuacion
        }else {
            return puntuacion
        }
    }

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BLANCOeSP),
            verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

        if(incorrectas == 3){
            currentIndex = questions.size
        }

        var contVidas = viewModel.contVidas
        var contEscudos = viewModel.escudos


        val totalTema = puntosH + score
        val totalGeneral = (userMenuViewModel.user?.totalPoints ?: 0) + score
      //  userMenuViewModel.updateQuizTotales(1)
        if (currentIndex == questions.size) {
            val visible = remember { mutableStateOf(false) }

            LaunchedEffect(currentIndex) {
                userMenuViewModel.updatePuntos(temaPuntos, totalTema)
                userMenuViewModel.updatePuntosTotal(totalGeneral)
                delay(200)
                visible.value = true
            }

            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BLANCOeSP).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Terminado Quiz de ${user?.tema}",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Card(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = colorEscogido)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ProfileInfoRow(R.drawable.rompecabezas, label = "    Preguntas correctas: ", value = "$correctas/${questions.size}")
                            ProfileInfoRow(R.drawable.elevar_a_mismo_nivel, label = "    Puntuación: ", value = "+ $score")
                            ProfileInfoRow(R.drawable.puntuacion_mas_alta__1_, label = "    Puntuación total: ", value = "$puntosH")
                            ProfileInfoRow(R.drawable.nivel, label = "    Dificultad: ", value = dificultad)
                            Spacer(Modifier.size(15.dp))
                            if(user?.mochila?.isNotEmpty() == true){
                                Text("pasivas activas",fontWeight =  FontWeight.Bold, color = Color.Black)
                                userMenuViewModel.listaObjetos.forEach{objetos ->

                                    Row( horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.padding(8.dp)) {

                                        Image(painter = painterResource(id = pintorView.imagenObjeto(objetos)),
                                            contentDescription = objetos.nombre,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(RoundedCornerShape(8.dp)))

                                        Text(text = objetos.nombre, fontWeight =  FontWeight.Bold, color = Color.Black)
                                    }



                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(25.dp))

                    Button(
                        onClick = {
                            userMenuViewModel.updateQuizTotales(1)
                            navHostController.popBackStack()
                            navHostController.navigate(Routes.menuRoadMap.routes)
                        },
                        colors = ButtonDefaults.buttonColors(colorEscogido),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Volver", color = colorLetras, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(30.dp))
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp),
                color = colorEscogido
            )
        }
        Spacer(modifier = Modifier.size(15.dp))

                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){


                    if(escudo.value){
                        Image(painterResource(R.drawable.blindaje), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))

                    }else if(escudo.value==false && contEscudos.value == 1){
                        Image(painterResource(R.drawable.escudo_roto), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }


                    if(segundoLatido.value){
                        Image(painterResource(R.drawable.corazon_herido), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))

                    }else if(segundoLatido.value==false && contVidas.value == 1){
                        Image(painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }



                    if (incorrectas <=0){
                        Image(painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }else if (incorrectas >= 1){
                        Image(painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }

                    if (incorrectas <=1){
                        Image(painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }else if (incorrectas >= 2){
                        Image(painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }


                    if (incorrectas !=3){
                        Image(painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }else if (incorrectas >= 3){
                        Image(painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                            modifier = Modifier.size(35.dp).padding(5.dp))
                    }



                    Text(
                        "Pregunta ${currentIndex + 1}/${questions.size}     PT: ${score}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(15.dp)
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
                    dificultadPuntos()
                    question?.opciones?.forEachIndexed { index, option ->
                        Button(
                            onClick = { comprobante = viewModel.answerQuestion(index,puntuacion)
                                      gameRoad.cantPulsado()},
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            colors = ButtonDefaults.buttonColors(colorEscogido),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(option, color = colorLetras, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Temporizador(
                10,
                colorChosen,
                currentIndex,
                questions,
                onTimeOut = { viewModel.answerQuestion(-1,puntuacion) })


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
                Image(painterResource(id = icono), contentDescription = "", modifier = Modifier.size(35.dp))
                Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Text(value, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }

}

@Composable
fun eleccionTest(dificultad: String): String{

        if(dificultad.equals("Aprendiz")){
            return ""

        }else if(dificultad.equals("Intermedio")){
            return "Intermedio"
        }else if(dificultad.equals("Avanzado")) {
            return "Avanzado"
        }else if(dificultad.equals("Profesional")) {
            return "Profesional"
        }else {
            return ""
        }

}

