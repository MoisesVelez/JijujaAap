package com.android.jijajuaap.partidaPublica

import android.R.color.white
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
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
        }
    }

    val pregunta = gmaplayViewModel.preguntasFinal
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)


    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)  },
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),


        ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {


            Text("${pregunta?.Pregunta}")
            Text("${pregunta?.Respuesta}")
        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraTop(user: User?, imag: Int, navHostController: NavHostController, colorEscogido: Color,) {
    TopAppBar(title = {Image(
        painter = painterResource(id = imag),
        contentDescription = "Logo App",
        modifier = Modifier.size(75.dp).padding(5.dp)
            .clip(CircleShape)
            .border(2.dp, color = Color.White, CircleShape)
            .clickable(onClick = {navHostController.navigate(Routes.MenuUser.routes)})
            .background(color = Color.White),

        )},
    modifier = Modifier.height(125.dp)
    , colors =TopAppBarDefaults.topAppBarColors(colorEscogido)
    , actions = {
        Text(user?.name.toString(), modifier =
            Modifier.padding(25.dp), fontWeight = FontWeight.Bold, color = Color.Black)

            Text("Puntos: " + user?.totalPoints.toString(), modifier =
                Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = Color.Black)

            Text(
                "Rango: " + user?.rango.toString(), modifier =
                Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = Color.Black)


    })

}


