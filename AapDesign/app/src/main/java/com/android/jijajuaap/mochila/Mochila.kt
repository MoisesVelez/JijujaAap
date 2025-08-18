package com.android.jijajuaap.mochila

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.pintor.barra
import com.android.jijajuaap.pintor.barraBaja
import com.android.jijajuaap.pintor.pintorView
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.colorCrema
import com.google.firebase.auth.FirebaseAuth

@Composable
fun inventario(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,pintorView: pintorView,mochilaViewModel: mochilaViewModel) {


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
    var objetoElegido = mochilaViewModel.objeto
    var listaEscogidos = mochilaViewModel.listaObjetos

    Scaffold(
        topBar = { barra(user, imag, navHostController, colorEscogido) },
        bottomBar = { barraBaja(navHostController, White) }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(fondo).padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona 3 pasivas para utilizar en tus Quiz.", color = Color.Black, textAlign = TextAlign.Center)

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .horizontalScroll(rememberScrollState())

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {


                    listaEscogidos.forEach { listaFinal ->



                        Box(
                            modifier = Modifier.size(75.dp)
                        ) {

                        Image(
                            painter = painterResource(id = pintorView.imagenObjeto(listaFinal)),
                            contentDescription = listaFinal.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(75.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Borrar",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .background(Color.Red, shape = CircleShape)
                                .clickable {mochilaViewModel.sacarLista(listaFinal)}
                        )
                    }

                }
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            Divider(
                color = White,
                thickness = 3.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )


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
                                .clickable(onClick = {mochilaViewModel.seleccionarObjeto(objetos)
                                mochilaViewModel.añadirLista(objetos)})

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
                                        .size(75.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Text(text = objetos.nombre, fontWeight =  FontWeight.Bold, color = Color.Black, fontSize = 15.sp)
                                Text(text = objetos.descripcion, color = Color.DarkGray, fontSize = 16.sp)
                            }
                        }

                    }
                }
            }



        }

    }
}