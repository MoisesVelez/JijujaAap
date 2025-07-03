package com.android.jijajuaap.crearQuiz

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CrearQuiz(userViewModel: UserMenuViewModel, navHostController: NavHostController) {


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userViewModel.loadUserData(it)
        }
    }
    val user = userViewModel.user

    val imag = userViewModel.imagenUsuario(user)
    val colorEscogido = userViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White,colorEscogido ))

    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)}
    )
    {innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(fondo)) {

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraTop(
    user: User?,
    imag: Int,
    navHostController: NavHostController,
    colorEscogido: Color
) {

    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }
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
                    Modifier.padding(25.dp), fontWeight = FontWeight.Bold, color = colorLetras
            )

            Text(
                "Puntos totales: ${user?.totalPoints}", modifier =
                    Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = colorLetras
            )


        })

}
