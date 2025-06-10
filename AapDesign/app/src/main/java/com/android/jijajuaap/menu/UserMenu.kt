package com.android.jijajuaap.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth


@Composable
fun menu(userMenuViewModel: UserMenuViewModel, navHostController: NavHostController){

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        if (currentUserUid != null) {
            userMenuViewModel.loadUserData(currentUserUid)
        }
    }
    UserProfileScreen(userMenuViewModel,navHostController)
}


    @SuppressLint("DiscouragedApi")
    @Composable
    fun UserProfileScreen(viewModel: UserMenuViewModel, navHostController: NavHostController) {
        var user = viewModel.user
        val drawableName = viewModel.imagenUsuario(user)
        val colorEscogido = viewModel.cambioColor(user?.team)
        val colorChosen = viewModel.colorUsuario(colorEscogido)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorChosen)
                .padding(24.dp).padding(top = 60.dp).padding(bottom = 50.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = drawableName),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, White, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(user?.name ?: "Sin nombre", style = MaterialTheme.typography.headlineSmall, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(user?.email ?: "", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileInfoRow(R.drawable.pueblo, label = "    Pueblo: ", value = user?.team ?: "Sin patria")
            ProfileInfoRow(R.drawable.puntuacion_mas_alta,label = "    Rango: ", value = user?.rango ?: "Iniciado")
            ProfileInfoRow(R.drawable.evaluacion,label = "     Puntos totales: ", value = (user?.totalPoints ?: 0).toString())
            ProfileInfoRow(R.drawable.quizas,label = "     Quiz totales: ", value = (user?.totalQuiz ?: 0).toString())

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { cambioDireccion(user?.team.toString(),navHostController)}, colors = ButtonDefaults.buttonColors(White)) {
                Text("Editar perfil", color =Color.Black, fontWeight = FontWeight.Bold)
            }
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

fun cambioDireccion(team: String,navHostController: NavHostController){
    if(team == "Rojin"){
        navHostController.navigate(Routes.MenuImagen.routes)
    }
    if(team=="Verdiano"){
        navHostController.navigate(Routes.MenuImagenVer.routes)
    }
    if(team=="Azulense"){
        navHostController.navigate(Routes.MenuImagenAzu.routes)

    }
}


