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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.login.MvvmPresentation
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.colorCrema
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
    val colorChosen = menuUserMenuViewModel.colorUsuario(colorEscogido)

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {4}
    )

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

    Box(modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center){


    Scaffold(
        topBar = { topAppBar(colorEscogido,navHostController,menuUserMenuViewModel) },
        modifier = Modifier.fillMaxSize(),
        bottomBar = { navigationBar(logingView, navHostController, colorEscogido) },

        ) { innerPadding ->




        PantallaConFondo(
            pagerState = pagerState,
            innerPadding = innerPadding,
            navHostController = navHostController,
            colorEscogido = colorEscogido
        )


        if (menuUserMenuViewModel.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .zIndex(1f)
                    .pointerInput(Unit) {

                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()

                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 5.dp,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

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
    TopAppBar(
        title = {Image(
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
                .clickable(onClick = {navHostController.navigate(Routes.ajuste.routes)}),
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
    val colorLetras = if (colorEscogido != Color.DarkGray) Color.Black else Color.White

    NavigationBar(
        containerColor = colorCrema,
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navHostController.navigate(Routes.Menu1.routes) },
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
            onClick = { navHostController.navigate(Routes.pintor.routes) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.lapiz),
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
            onClick = { /* Acción */ },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.amistad),
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
fun PantallaConFondo(
    pagerState: PagerState,
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    colorEscogido: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        when (pagerState.currentPage) {
            0 -> Image(
                painter = painterResource(R.drawable._755078083836),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.5f)
            )
            1 -> Image(
                painter = painterResource(R.drawable._755078083801),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.5f)
            )
            2 -> Image(
                painter = painterResource(R.drawable._755078083764),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.5f)
            )
            3 -> Image(
                painter = painterResource(R.drawable.podio_de_cuadrados_amistosos),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.5f)
            )
        }


        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> SimpleCardPantallaCompleta(
                        titulo = "Campaña",
                        contenido = "Completa los niveles y supera todos los desafios en las diferentes categorías.",
                        height = 500.dp,
                        painter = painterResource(R.drawable._755078083836),
                        onClick = { navHostController.navigate(Routes.menuPartidaPublica.routes) }
                    )
                    1 -> SimpleCardPantallaCompleta(
                        titulo = "Comunidad",
                        contenido = "Participa con la comunidad superando sus Quiz,busca o elige entre los ultimos publicados.",
                        height = 500.dp,
                        painter = painterResource(R.drawable._755078083801),
                        onClick = { navHostController.navigate(Routes.menuInicioComunidad.routes) }
                    )
                    2 -> SimpleCardPantallaCompleta(
                        titulo = "Creador",
                        contenido = "Contenido de la tarjeta número ${page + 1}",
                        height = 500.dp,
                        painter = painterResource(R.drawable._755078083764),
                        onClick = { navHostController.navigate(Routes.menuCrearQuiz.routes) }
                    )
                    3 -> SimpleCardPantallaCompleta(
                        titulo = "Ranking",
                        contenido = "Contenido de la tarjeta número ${page + 1}",
                        height = 500.dp,
                        painter = painterResource(R.drawable.podio_de_cuadrados_amistosos),
                        onClick = { navHostController.navigate(Routes.ranking.routes) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            IndicadorBarraAnimada(
                pagerState = pagerState,
                totalPaginas = 4,
                modifier = Modifier
                    .padding(bottom = 74.dp)
                    .fillMaxWidth(),
                colorEscogido = Color.White,
                colorEscogid = colorEscogido
            )
        }
    }
}



@Composable
fun SimpleCardPantallaCompleta(
    titulo: String, contenido: String, height: Dp, painter: Painter,onClick: () -> Unit

    ) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(30.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(BLANCOeSP)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = contenido,color = Color.Black, modifier = Modifier.padding(30.dp))
            Image(painter = painter, contentDescription = "",contentScale = ContentScale.Crop,modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun IndicadorBarraAnimada(
    pagerState: PagerState,
    totalPaginas: Int,
    modifier: Modifier = Modifier,
    colorEscogido: Color,
    colorEscogid: Color
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
                .background(colorEscogid, shape = RoundedCornerShape(50))
        )
    }
}





