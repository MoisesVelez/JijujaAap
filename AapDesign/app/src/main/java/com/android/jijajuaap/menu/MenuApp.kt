package com.android.jijajuaap.menu

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.initial.Colores
import com.android.jijajuaap.presentation.login.MvvmPresentation

@Composable
fun menuInitial(logingView: MvvmPresentation, navHostController: NavHostController) {
    val colorEscogido = Colores()
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))
    Scaffold(
        topBar = { topAppBar(colorEscogido) },
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
        bottomBar = { navigationBar(logingView, navHostController, colorEscogido) },

        ) { innerPadding ->
        PantallaConPager(fondo,innerPadding,colorEscogido)


    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(colorEscogido: Color) {
    TopAppBar(title = {Image(
        painter = painterResource(id = R.drawable.portada),
        contentDescription = "Logo App",
        modifier = Modifier.size(100.dp).padding(10.dp)
            .clip(CircleShape)
            .border(2.dp, color = colorEscogido, CircleShape),

    )},
        modifier = Modifier.height(150.dp)
       , colors =TopAppBarDefaults.topAppBarColors(Color.White) )

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
            modifier = Modifier.padding(top = 30.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {navHostController.navigate(Routes.Menu1.routes)},
            icon = {Icon(painter = painterResource(R.drawable.hogar),modifier= Modifier.size(35.dp), contentDescription = "")}
            ,label = { Text("Menu", fontWeight = FontWeight.Bold, color = Color.Black) })

        NavigationBarItem(selected = true,
            modifier = Modifier.padding(top = 30.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.DarkGray,
                unselectedIconColor = Color.DarkGray,
                selectedTextColor = Color(0xFFFFC107),
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            ),
            onClick = {},
            icon = {Icon(painter = painterResource(R.drawable.herramienta_lapiz),modifier= Modifier.size(35.dp), contentDescription = "")}
            ,label = { Text("Partida privada", fontWeight = FontWeight.Bold, color = Color.Black) })

        NavigationBarItem(selected = true,
            modifier = Modifier.padding(top = 30.dp),
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
            icon = {Icon(painter = painterResource(R.drawable.amistad),modifier= Modifier.size(35.dp), contentDescription = "")}
            ,label = { Text("Social", fontWeight = FontWeight.Bold, color = Color.Black) }
        )

    }
}
@Composable
fun PantallaConPager(fondo: Brush, innerPadding: PaddingValues,colorEscogido: Color) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
                    titulo = "Tarjeta",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp
                )
                1 -> SimpleCardPantallaCompleta(
                    titulo = "Tarjet",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp
                )
                2 -> SimpleCardPantallaCompleta(
                    titulo = "Tarje",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp
                )
                3 -> SimpleCardPantallaCompleta(
                    titulo = "Tar",
                    contenido = "Contenido de la tarjeta número ${page + 1}",
                    height = 500.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        IndicadorBarraAnimada(
            pagerState = pagerState,
            totalPaginas = 4,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
            , colorEscogido = colorEscogido
        )
    }
}


@Composable
fun SimpleCardPantallaCompleta(titulo: String, contenido: String, height: Dp) {
    Card(
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
            Text(text = titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = contenido)
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



