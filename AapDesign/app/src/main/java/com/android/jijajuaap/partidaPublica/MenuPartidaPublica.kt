package com.android.jijajuaap.partidaPublica


import android.R.string
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.menu.navigationBar
import com.android.jijajuaap.menu.topAppBar
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.login.MvvmPresentation


@Composable
fun menuPartidaPublica(userViewModel: UserMenuViewModel,navHostController: NavHostController,logingView:MvvmPresentation,gmaplayViewModel: gmaplayViewModel){

    val usuario = userViewModel.user
    val colorEscogido = userViewModel.cambioColor(usuario?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))
    val imag = userViewModel.imagenUsuario(usuario)



    Box(modifier = Modifier.background(Color.Black)) {
        Scaffold(
            topBar = { topAppBar(colorEscogido, navHostController, userViewModel) },
            modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
            bottomBar = { navigationBar(logingView, navHostController, colorEscogido) },

            ) { innerPadding ->


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize().background(fondo).padding(innerPadding).padding(20.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {


                Spacer(modifier = Modifier.height(16.dp))
                cardPublica(R.drawable.historiapaint,"historia",userViewModel,navHostController)

                Spacer(modifier = Modifier.height(12.dp))
                cardPublica(
                    R.drawable.get_free_vectors__images__pictures___clips___vecteezy,
                    "literatura",
                    userViewModel,
                    navHostController
                )

                Spacer(modifier = Modifier.height(12.dp))
                cardPublica(
                    R.drawable.get_free_vectors__images__pictures___clips___vecteezy,
                    "filosofia",
                    userViewModel,
                    navHostController
                )

                Spacer(modifier = Modifier.height(12.dp))
                cardPublica(
                    R.drawable.get_free_vectors__images__pictures___clips___vecteezy,
                    "deportes",
                    userViewModel,
                    navHostController
                )

                Spacer(modifier = Modifier.height(12.dp))
                cardPublica(
                    R.drawable.get_free_vectors__images__pictures___clips___vecteezy,
                    "culturaPopular",
                    userViewModel,
                    navHostController
                )

                Spacer(modifier = Modifier.height(12.dp))
                cardPublica(R.drawable.selva, "naturaleza", userViewModel, navHostController)




            }
        }
    }
}

@Composable
fun cardPublica(
    image: Int,
    string: String,
    userMenuViewModel: UserMenuViewModel,
    navHostController: NavHostController
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = {userMenuViewModel.updateTema(string)
            navHostController.navigate(Routes.menuRoadMap.routes)}),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = string, color = Color.Black,
                modifier = Modifier
                    .padding(16.dp))


            Image(
                painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )

        }


    }

}