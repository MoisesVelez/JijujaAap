package com.android.jijajuaap.comunidad

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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

import com.google.firebase.auth.FirebaseAuth
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
                shape = RoundedCornerShape(16.dp),
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
            Text("${preguntasTest}")
            Button(onClick = { numCont += 1 }) { }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(preguntasTest?.pregunta ?: "", color = Color.Black)
            }
            Spacer(modifier = Modifier.size(15.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(preguntasTest?.opciones ?: emptyList()) { opcion ->

                    Card(modifier = Modifier
                        .clickable(onClick = {numCont +=1})
                        .fillMaxWidth()
                        .padding(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Text(opcion, color = Color.Black)
                    }

                }
            }



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





