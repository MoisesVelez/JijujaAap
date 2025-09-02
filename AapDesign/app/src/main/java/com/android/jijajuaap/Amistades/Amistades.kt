package com.android.jijajuaap.Amistades

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.comunidad.barraTop
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.colorCrema
import com.google.firebase.auth.FirebaseAuth


@Composable
fun amistades(navHostController: NavHostController,userMenuViewModel: UserMenuViewModel){

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val user = userMenuViewModel.user
    val amigos = remember { mutableStateListOf<User>() }
    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
            userMenuViewModel.vaciarLista()
            amigos.clear()
            amigos.addAll(user?.amigos ?: emptyList())

        }
    }


    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    var buscar by remember { mutableStateOf("") }
    val lista by userMenuViewModel.listaAmigo.collectAsState()




    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)},
        bottomBar = {barraBtom(navHostController,colorEscogido)}
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(R.drawable.chatgpt_image_14_ago_2025__00_24_46),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }

        Column(
            modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                , modifier = Modifier.padding(10.dp).padding(bottom = 0.dp)) {
                TextField(
                    value = buscar,
                    onValueChange = { buscar = it },
                    label = { Text("Jugadores", color = Color.Black) },

                    singleLine = true,
                    modifier = Modifier.weight(1f).padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = BLANCOeSP
                    ),
                )

              //  val coroutineScope = rememberCoroutineScope()
                Button(
                    onClick = {
                        userMenuViewModel.buscarAmigos(buscar)
                    },
                    modifier = Modifier.weight(0.3f),
                    colors = ButtonDefaults.buttonColors(colorEscogido),
                    shape = RoundedCornerShape(12.dp)
                )
                {
                    Image(
                        painterResource(R.drawable.lupa), contentDescription = "Buscar",
                        modifier = Modifier.size(35.dp)
                    )
                }


            }
            Card ( modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp).alpha(0.8f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BLANCOeSP)){


                lista.forEach {
                    if(it != user){
                        cardsForm(
                            userMenuViewModel, it, user,
                            onAddFriend = { amigos.add(it) },
                            ondeleteFiends = {amigos.remove(it)},
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            Divider(
                color = White,
                thickness = 3.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text("Lista de amigos", color = Color.Black, fontWeight = FontWeight.Bold)

            amigos.forEach {
                cardsForm2(userMenuViewModel,it,user)
            }

        }




        }
    }




@Composable
fun barraBtom(navHostController: NavHostController, colorEscogido: Color) {

    val colorLetras = if (colorEscogido != Color.DarkGray) Color.Black else Color.White

    NavigationBar(
        containerColor = colorCrema,
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                navHostController.popBackStack()
                navHostController.navigate(Routes.Menu1.routes)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.buscar_casa),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navHostController.popBackStack()
                navHostController.navigate(Routes.comQuiz.routes)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.pueblo),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )

        NavigationBarItem(
            selected = false,
            onClick = {navHostController.navigate(Routes.menuCrearQuiz.routes)},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.crear),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )
    }
}

@Composable
fun cardsForm(userMenuViewModel: UserMenuViewModel, lista: User, user: User?,onAddFriend: (User) -> Unit,ondeleteFiends:(User) -> Unit) {

    val imag = userMenuViewModel.imagenUsuario(lista)
    val colorEscogido = userMenuViewModel.cambioColor(lista.team)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp).alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "${lista.name}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    "Puntos: ${lista.totalPoints}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    "Rango: ${lista.rango}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

            }

            Image(
                painter = painterResource(id = imag),
                contentDescription = "Logo App",
                modifier = Modifier.size(70.dp).padding(5.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = colorEscogido, CircleShape)
                    .background(color = Color.White),

                )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Green, shape = CircleShape)
                    .clickable {
                        userMenuViewModel.añadirAmigo(
                            lista,
                            user?.uid.toString()
                        )
                        onAddFriend(lista)

                    }
            )
            Spacer(Modifier.size(15.dp))
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Borrar",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Red, shape = CircleShape)
                    .clickable {
                        userMenuViewModel.quitarAmigo(
                            lista,
                            user?.uid.toString()
                        )
                        ondeleteFiends(lista)
                    }
            )

        }
    }
}

@Composable
fun cardsForm2(userMenuViewModel: UserMenuViewModel, lista: User, user: User?) {

    val imag = userMenuViewModel.imagenUsuario(lista)
    val colorEscogido = userMenuViewModel.cambioColor(lista.team)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp).alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Jugador: ${lista.name}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    "Puntos: ${lista.totalPoints}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    "Rango: ${lista.rango}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

            }

            Image(
                painter = painterResource(id = imag),
                contentDescription = "Logo App",
                modifier = Modifier.size(70.dp).padding(5.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = colorEscogido, CircleShape)
                    .background(color = Color.White),

                )
            Spacer(Modifier.size(15.dp))
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Borrar",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Red, shape = CircleShape)
                    .clickable {


                        userMenuViewModel.quitarAmigo(
                            lista,
                            user?.uid.toString()
                        )


                    })


        }
    }
}

