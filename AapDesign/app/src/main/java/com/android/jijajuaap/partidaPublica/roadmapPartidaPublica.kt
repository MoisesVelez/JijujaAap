package com.android.jijajuaap.partidaPublica

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun roadMap(userMenuViewModel: UserMenuViewModel,gmaplayViewModel: gmaplayViewModel,navHostController: NavHostController){

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
        }
    }

    val user = userMenuViewModel.user
    val imag = userMenuViewModel.imagenUsuario(user)




    LaunchedEffect(user?.tema) {
        user?.tema?.let {
            gmaplayViewModel.preguntas(user)
            gmaplayViewModel.puntos(user)
        }
    }
    val puntosH = gmaplayViewModel.puntosFinal
    val pregunta = gmaplayViewModel.preguntasFinal
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val colorChosen = userMenuViewModel.colorUsuario(colorEscogido)


    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido,puntosH)  },
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp).background(BLANCOeSP),


        ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.White)) {






            ProgressWithCardsSideBySide(puntosH,500,colorChosen,user)

        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraTop(
    user: User?,
    imag: Int,
    navHostController: NavHostController,
    colorEscogido: Color,
    puntosH: Int,) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = imag),
                contentDescription = "Logo App",
                modifier = Modifier.size(75.dp).padding(5.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.White, CircleShape)
                    .clickable(onClick = { navHostController.navigate(Routes.MenuUser.routes) })
                    .background(color = Color.White),

                )
        },
        modifier = Modifier.height(125.dp),
        colors = TopAppBarDefaults.topAppBarColors(colorEscogido),
        actions = {
            Text(
                user?.name.toString(), modifier =
                    Modifier.padding(25.dp), fontWeight = FontWeight.Bold, color = Color.Black
            )

            Text(
                "Puntos: $puntosH", modifier =
                    Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = Color.Black
            )


        })

}


@Composable
fun ProgressWithCardsSideBySide(score: Int?, maxScore: Int, colorEscogido: Color, user: User?) {
    val safeScore = score ?: 0
    val progress = (safeScore.coerceIn(0, maxScore).toFloat() / maxScore.toFloat()).coerceIn(0f, 1f)

    Row(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .padding(18.dp).background(BLANCOeSP,shape = RoundedCornerShape(5)),
            verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("${user?.tema}", fontWeight = FontWeight.Bold, color = Color.Black)
            Card(
                modifier = Modifier
                    .clickable(onClick = {})
                    .height(75.dp)
                    .padding(6.dp)
                    .fillMaxWidth()
                    ,
                elevation = CardDefaults.cardElevation(4.dp)
                ,colors = CardDefaults.cardColors(Color.White)
            ) { Text("hola") }

           // Spacer(modifier = Modifier.weight(0.2f))
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(10.dp)
                    .height(70.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(8.dp))
            ) {}

            Card(
                modifier = Modifier
                    .clickable(onClick = {})
                    .height(75.dp)
                    .padding(6.dp)
                    .fillMaxWidth()
                    .background(White),
                elevation = CardDefaults.cardElevation(4.dp)
                ,colors = CardDefaults.cardColors(Color.White)
            ) { Text("hola") }

           // Spacer(modifier = Modifier.weight(0.2f))
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(10.dp)
                    .height(70.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(8.dp))
            ) {}

            Card(
                modifier = Modifier
                    .clickable(onClick = {})
                    .height(75.dp)
                    .padding(6.dp)
                    .fillMaxWidth()
                    .background(White),
                elevation = CardDefaults.cardElevation(4.dp)
                ,colors = CardDefaults.cardColors(Color.White)
            ) { Text("hola") }

            // Spacer(modifier = Modifier.weight(0.2f))
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(10.dp)
                    .height(70.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(8.dp))
            ) {}

                Card(
                    modifier = Modifier
                        .clickable(onClick = {})
                        .height(75.dp)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .background(White),
                    elevation = CardDefaults.cardElevation(4.dp)
                    ,colors = CardDefaults.cardColors(Color.White)
                ) { Text("hola") }


            }


            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight().padding(10.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .fillMaxHeight(0.8f)
                        .background(Color.LightGray, shape = RoundedCornerShape(50))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(progress)
                            .align(Alignment.BottomCenter)
                            .background(colorEscogido)

                    )
                }
            }
        }
    }





