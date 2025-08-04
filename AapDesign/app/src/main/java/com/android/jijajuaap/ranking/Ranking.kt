package com.android.jijajuaap.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.presentation.login.MvvmPresentation
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ranking(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,rankingView: rankingView
,logingView: MvvmPresentation) {

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
            rankingView.obtener15()
        }
    }
    val user = userMenuViewModel.user
    val cornerRadius = 16.dp
    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White, colorEscogido))
    val usuarios = rankingView.listausuarios
    val focusManager = LocalFocusManager.current
    val blanco50 = Color.White.copy(alpha = 0.5f)
    val colorChosen = userMenuViewModel.colorUsuario(colorEscogido)



    Scaffold(
        topBar = { barraTp(user, imag, navHostController, colorEscogido) },
        bottomBar = {navigationBAr(navHostController,BLANCOeSP)}
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(BLANCOeSP)) {

            Column(
                modifier = Modifier.padding(15.dp).fillMaxSize().background(
                    BLANCOeSP
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Top 15", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.size(15.dp))
                Column(
                    Modifier
                        .clip(RoundedCornerShape(cornerRadius))
                        .fillMaxSize()
                        .background(fondo)
                        .border(width = 2.dp, Color.White, shape = RoundedCornerShape(cornerRadius))
                        .padding(15.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    usuarios.forEach {
                        cardsRanking(it.name.toString(), it.totalPoints, it.rango.toString())
                    }

                }


            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraTp(
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
fun cardsRanking(
    Usuario: String,
    puntos: Int?,
    rango: String

){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Jugador: ${Usuario}", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.width(50.dp))
            Text("Puntos: ${puntos}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.width(50.dp))
            Text("Rango: ${rango}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)

        }
    }
}


@Composable
fun navigationBAr(

    navHostController: NavHostController,
    colorEscogido: Color
) {
    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }

    NavigationBar(containerColor = colorEscogido, modifier = Modifier.height(110.dp)
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
            onClick = {navHostController.navigate(Routes.pintor.routes)},
            icon = {Icon(painter = painterResource(R.drawable.lapiz),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "", tint = colorLetras)}
            ,label = { Text("Pintor", fontWeight = FontWeight.Bold, color = colorLetras) })

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


