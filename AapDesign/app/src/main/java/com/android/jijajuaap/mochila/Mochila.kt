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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.objects.Objetos
import com.android.jijajuaap.pintor.barra
import com.android.jijajuaap.pintor.barraBaja
import com.android.jijajuaap.pintor.pintorView
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.colorCrema
import com.android.jijajuaap.ui.theme.verdeUser
import com.google.firebase.auth.FirebaseAuth

@Composable
fun inventario(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,pintorView: pintorView) {


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val user = userMenuViewModel.user
    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
            pintorView.obtenerObjetos()
            userMenuViewModel.cargarMochila(it)



        }
    }

    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White, colorCrema))
    val listaObjetos = user?.inventario
    var objetoElegido = userMenuViewModel.listaObjetos


    Scaffold(
        topBar = { barra(user, imag, navHostController, colorEscogido) },
        bottomBar = { barraBaja(navHostController, colorCrema) }
    )
    { innerPadding ->


        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(R.drawable.chatgpt_image_19_ago_2025__18_59_02),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()  )

        }

        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona 3 pasivas para utilizar en tus Quiz.", color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth()
                    .height(100.dp) .alpha(0.8f)

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp).fillMaxWidth()
                ) {


                    objetoElegido.forEach { listaFinal ->

                        Spacer(modifier = Modifier.size(5.dp))
                        Box(
                            modifier = Modifier.size(75.dp),contentAlignment = Alignment.Center
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
                                    .clickable {userMenuViewModel.quitarObjeto(listaFinal,
                                        user?.uid.toString()
                                    )}
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


                        val colorEscogido = if (objetoElegido.contains(objetos)) verdeUser else Color.White


                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = colorEscogido),
                            modifier = Modifier
                                .padding(vertical = 14.dp)
                                .fillMaxWidth()
                                .height(130.dp).alpha(0.8f)
                                .clickable(onClick = {
                                userMenuViewModel.añadirLista(objetos,
                                    user.uid.toString()
                                )})

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
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