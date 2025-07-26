package com.android.jijajuaap.comunidad


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.rememberCoroutineScope
import com.android.jijajuaap.R
import com.android.jijajuaap.objects.PreguntaComunidad
import kotlinx.coroutines.launch


@Composable
fun menuInicialComunidad(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,comunidadView: comunidadView){


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
        }
    }
    val user = userMenuViewModel.user
    LaunchedEffect(user) {
        comunidadView.obtenerQuiz()
    }




    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White,colorEscogido ))
    val lista by comunidadView.listaTest.collectAsState()
    var buscar by remember { mutableStateOf("") }
    val resultados = remember { mutableStateOf<List<PreguntaComunidad>>(emptyList())}
    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)},
        bottomBar = {barraBottom(navHostController,colorEscogido)}
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).background(fondo)) {


            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier.padding(10.dp).padding(bottom = 0.dp)) {
            TextField(
                value = buscar,
                onValueChange = {buscar = it},
                label = { Text("Comunidad", color = Color.Black) },

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
                    unfocusedContainerColor = colorEscogido,
                    focusedContainerColor = BLANCOeSP
                ),
            )

                val coroutineScope = rememberCoroutineScope()
                Button(onClick = {
                    coroutineScope.launch {
                        val listado = comunidadView.buscador(buscar)
                        resultados.value = listado

                    }
                },
                    modifier = Modifier.weight(0.3f),
                    colors = ButtonDefaults.buttonColors(colorEscogido),
                    shape = RoundedCornerShape(12.dp))
                {
                Image(painterResource(R.drawable.lupa), contentDescription = "Buscar",
                    modifier = Modifier.size(35.dp))
            }
        }
            Card(modifier = Modifier.padding(20.dp)
                .fillMaxSize().clip(RoundedCornerShape(16.dp))
                .background(White)
                .padding(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(BLANCOeSP)) {


                    LazyColumn( modifier = Modifier.fillMaxSize().padding(15.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                        if(resultados.value.isNotEmpty() && buscar.toString() != "") {
                            items(resultados.value) { test ->
                                    cardsComunidad(test.titulo, test.autor,navHostController,test.id,comunidadView)
                            }
                        }else{
                            items(lista) { test ->
                                cardsComunidad(
                                    test.titulo,
                                    test.autor,
                                    navHostController,
                                    test.id,
                                    comunidadView
                                )

                            }
                        }
                }

            }

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

@Composable
fun barraBottom(navHostController: NavHostController, colorEscogido: Color) {


    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }
    NavigationBar(containerColor = White, modifier = Modifier.height(110.dp)
    ){
        NavigationBarItem(selected = true,
            modifier = Modifier,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {navHostController.navigate(Routes.Menu1.routes)},
            icon = {Icon(painter = painterResource(R.drawable.buscar_casa),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "", tint = colorLetras)}
            ,label = { Text("Menu", fontWeight = FontWeight.Bold, color = colorLetras) })

        NavigationBarItem(selected = true,
            modifier = Modifier,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {navHostController.navigate(Routes.menuCrearQuiz.routes)},
            icon = {Icon(painter = painterResource(R.drawable.crear),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "", tint = colorLetras)}
            ,label = { Text("Crear Quiz", fontWeight = FontWeight.Bold, color = colorLetras) })

        NavigationBarItem(selected = true,
            modifier = Modifier,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {},
            icon = {Icon(painter = painterResource(R.drawable.amistad),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "", tint = colorLetras)}
            ,label = { Text("Social", fontWeight = FontWeight.Bold, color = colorLetras) }
        )

    }
}

@Composable
fun cardsComunidad(
    nombre: String,
    Usuario: String,
    navHostController: NavHostController,
    id: String?,
    comunidadView: comunidadView
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable(onClick = {
                comunidadView.iD = id.toString()
                comunidadView.titulo = nombre
                comunidadView.creador = Usuario
                navHostController.navigate(Routes.comQuiz.routes)})
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Quiz: ${nombre}", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.width(50.dp))
            Text("Creador: ${Usuario}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        }
    }
}


