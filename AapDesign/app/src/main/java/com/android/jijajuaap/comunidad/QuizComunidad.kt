package com.android.jijajuaap.comunidad

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes

import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.android.jijajuaap.partidaPublica.ProfileInfoRow
import com.android.jijajuaap.ui.theme.BLANCOeSP
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
    var incorrectas = comunidadView.incorrecto



    Scaffold(
        topBar = { barraTo(user, imag, navHostController, colorEscogido) },

        ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .background(fondo)
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(5.dp))
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
                    modifier = Modifier.padding(10.dp)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (incorrectas == 0) {
                    Image(
                        painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                } else if (incorrectas >= 1) {
                    Image(
                        painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                }

                if (incorrectas <= 1) {
                    Image(
                        painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                } else if (incorrectas >= 2) {
                    Image(
                        painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                }


                if (incorrectas != 3) {
                    Image(
                        painterResource(R.drawable.me_gusta), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                } else if (incorrectas == 3) {
                    Image(
                        painterResource(R.drawable.corazon_roto), contentDescription = "vidas",
                        modifier = Modifier.size(35.dp).padding(5.dp)
                    )
                }
            }
            Divider(
                color = White,
                thickness = 3.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if(incorrectas == 3){
                comunidadView.finalizador()
            }

            val finalizador = comunidadView.finalizador == true
            val totalGeneral = (userMenuViewModel.user?.totalPoints ?: 0) + comunidadView.buenPunto

            AnimatedVisibility(
                visible = finalizador,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BLANCOeSP),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Text(
                        "Terminado Quiz de ${comunidadView.titulo}",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Card(
                        modifier = Modifier
                            .height(350.dp)
                            .padding(20.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = colorEscogido)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ProfileInfoRow(
                                R.drawable.rompecabezas,
                                label = "    Preguntas correctas: ",
                                value = "${comunidadView.contador}/${comunidadView.preguntasCom?.preguntas?.size}"
                            )
                            ProfileInfoRow(
                                R.drawable.elevar_a_mismo_nivel,
                                label = "    Puntuación: ",
                                value = "${comunidadView.buenPunto}"
                            )
                            ProfileInfoRow(
                                R.drawable.puntuacion_mas_alta__1_,
                                label = "    Puntuación total: ",
                                value = "${totalGeneral}"

                            )
                            ProfileInfoRow(R.drawable.nivel,
                                label = "    Dificultad: ",
                                value = "Comunidad")


                        }
                    }

                    Spacer(modifier = Modifier.size(25.dp))

                    Button(
                        onClick = {
                            userMenuViewModel.updatePuntosTotal(totalGeneral)
                            userMenuViewModel.updateQuizTotales(1)
                            navHostController.popBackStack()
                            navHostController.navigate(Routes.menuInicioComunidad.routes)
                        },
                        colors = ButtonDefaults.buttonColors(colorChosen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Volver", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.size(7.dp))

            Card(
                modifier = Modifier
                    .height(75.dp)
                    .padding(6.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {

                    Row(
                        modifier = Modifier.fillMaxSize().background(White).padding(15.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            preguntasTest?.pregunta ?: "",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black
                        )
                }

            }

            Spacer(modifier = Modifier.size(15.dp))



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

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(400.dp)
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(preguntasTest?.opciones ?: emptyList()) { index, opcion ->
                            Button(
                                onClick = {
                                    numCont += 1
                                    comunidadView.comprobador(
                                        index,
                                        preguntasTest?.correctAnswerIndex ?: -1
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                colors = ButtonDefaults.buttonColors(colorEscogido),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(opcion, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                }

            }

            Temporizado(
                10,
                colorChosen,
                numCont,
                preguntas?.preguntas ?: emptyList(),
                onTimeOut = { numCont += 1 })
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





