package com.android.jijajuaap.mochila

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.jijajuaap.comunidad.cardsComunidad
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.pintor.barra
import com.android.jijajuaap.pintor.barraBaja
import com.android.jijajuaap.pintor.pintorView
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.colorCrema
import com.google.firebase.auth.FirebaseAuth

@Composable
fun inventario(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,pintorView: pintorView) {


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
            pintorView.obtenerObjetos()

        }
    }
    val user = userMenuViewModel.user
    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White, colorCrema))
    val listaObjetos = user?.inventario

    Scaffold(
        topBar = { barra(user, imag, navHostController, colorEscogido) },
        bottomBar = { barraBaja(navHostController, White) }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(fondo)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (user?.inventario.isNullOrEmpty()) {
                Text("No tienes nada en el inventario...", fontWeight =  FontWeight.Bold, color = Color.Black,
                    modifier = Modifier.padding(15.dp))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Mochila", fontWeight =  FontWeight.Bold, color = Color.Black)
                    listaObjetos?.forEach { objetos ->

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .padding(vertical = 14.dp)
                                .fillMaxWidth()
                                .height(130.dp)

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = pintorView.imagenObjeto(objetos)),
                                    contentDescription = objetos.nombre,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Text(text = objetos.nombre, fontWeight =  FontWeight.Bold, color = Color.Black, fontSize = 19.sp)
                                Text(text = objetos.descripcion, color = Color.DarkGray, fontSize = 16.sp)
                            }
                        }

                    }
                }
            }



        }

    }
}