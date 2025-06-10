package com.android.jijajuaap.menu

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.login.MvvmPresentation
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnrememberedMutableState")
@Composable
fun menuInitial(logingView: MvvmPresentation, navHostController: NavHostController,menuUserMenuViewModel: UserMenuViewModel) {

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        if (currentUserUid != null) {
            menuUserMenuViewModel.loadUserData(currentUserUid)
        }
    }
    UserProfileScreen(menuUserMenuViewModel, navHostController)

    val user = menuUserMenuViewModel.user
    val colorEscogido = menuUserMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))


    val context = LocalContext.current

    val showColorDialog = remember(currentUserUid) {
        mutableStateOf(
            currentUserUid != null && !menuUserMenuViewModel.hasUserChosenTeam(context, currentUserUid)
        )
    }

    if (showColorDialog.value) {
        menuUserMenuViewModel.MyCustomDialog(
            onDismiss = {
                if (currentUserUid != null) {
                    menuUserMenuViewModel.setUserHasChosenTeam(context, currentUserUid)
                }
                showColorDialog.value = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)){


    Scaffold(
        topBar = { topAppBar(colorEscogido,navHostController,menuUserMenuViewModel) },
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
        bottomBar = { navigationBar(logingView, navHostController, colorEscogido) },

        ) { innerPadding ->
        PantallaConPager(fondo,innerPadding,colorEscogido)


    }
}
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(
    colorEscogido: Color,
    navHostController: NavHostController,
    menuUserMenuViewModel: UserMenuViewModel,) {
    val usuario = menuUserMenuViewModel.user
    val imagenUser = menuUserMenuViewModel.imagenUsuario(usuario)
    TopAppBar(title = {Image(
        painter = painterResource(id = imagenUser),
        contentDescription = "Logo App",
        modifier = Modifier.size(75.dp).padding(5.dp)
            .clip(CircleShape)
            .border(2.dp, color = colorEscogido, CircleShape)
            .clickable(onClick = {navHostController.navigate(Routes.MenuUser.routes)})
            .background(color = Color.White),

    )},
        modifier = Modifier.height(125.dp)
       , colors =TopAppBarDefaults.topAppBarColors(Color.White)
    , actions = {
        Icon(
            painter = painterResource(R.drawable.ajustamiento),
            contentDescription = "Ajustes",
            modifier = Modifier.size(60.dp)
                .clickable(onClick = {}),
            tint = Color.Black
        )

        })

}


@Composable
fun navigationBar(

    menuviewModel: MvvmPresentation,
    navHostController: NavHostController,
    colorEscogido: Color
) {
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
            icon = {Icon(painter = painterResource(R.drawable.buscar_casa),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "")}
            ,label = { Text("Menu", fontWeight = FontWeight.Bold, color = Color.Black) })

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
            icon = {Icon(painter = painterResource(R.drawable.lapiz),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "")}
            ,label = { Text("Partida privada", fontWeight = FontWeight.Bold, color = Color.Black) })

        NavigationBarItem(selected = true,
            modifier = Modifier,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {menuviewModel.logOut {navHostController.navigate(Routes.Screen1.routes){
                popUpTo(Routes.Screen1.routes)
                { inclusive = true }
            }}},
            icon = {Icon(painter = painterResource(R.drawable.amistad),modifier= Modifier.size(60.dp).padding(top = 20.dp), contentDescription = "")}
            ,label = { Text("Social", fontWeight = FontWeight.Bold, color = Color.Black) }
        )

    }
}


@Composable
fun PantallaConPager(fondo: Brush, innerPadding: PaddingValues,colorEscogido: Color) {
    val pagerState = rememberPagerState(pageCount = { 4 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(innerPadding)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> SimpleCardPantallaCompleta(
                    titulo = "Partida pública",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp,
                    painter = painterResource(
                        R.drawable.ilustracionpublica)
                )
                1 -> SimpleCardPantallaCompleta(
                    titulo = "Competitivo",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp,
                    painter = painterResource(
                        R.drawable.ilustracion_sin_titulo_3)
                )
                2 -> SimpleCardPantallaCompleta(
                    titulo = "Creador",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp,
                    painter = painterResource(
                        R.drawable.ilustracion_sin_titulo_6)
                )
                3 -> SimpleCardPantallaCompleta(
                    titulo = "Lore...",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp,
                    painter = painterResource(
                        R.drawable.ilustracion_sin_titulo__3_)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        IndicadorBarraAnimada(
            pagerState = pagerState,
            totalPaginas = 4,
            modifier = Modifier
                .padding(bottom = 74.dp)
                .fillMaxWidth()
            , colorEscogido = colorEscogido
        )
    }
}


@Composable
fun SimpleCardPantallaCompleta(titulo: String, contenido: String, height: Dp, painter: Painter) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(30.dp)
            .clickable(onClick = {}),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = contenido,color = Color.Black)
            Image(painter = painter, contentDescription = "")
        }
    }
}

@Composable
fun IndicadorBarraAnimada(
    pagerState: PagerState,
    totalPaginas: Int,
    modifier: Modifier = Modifier,
    colorEscogido: Color
) {
    val progresoAnimado = remember {
        Animatable(0f)
    }


    LaunchedEffect(pagerState.currentPage, pagerState.currentPageOffsetFraction) {
        val offset = pagerState.currentPage + pagerState.currentPageOffsetFraction
        progresoAnimado.animateTo(offset / (totalPaginas - 1).coerceAtLeast(1))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 32.dp)

    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorEscogido, shape = RoundedCornerShape(50))
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progresoAnimado.value)
                .background(Color.White, shape = RoundedCornerShape(50))
        )
    }
}



