package com.android.jijajuaap.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.initial.Colores
import com.android.jijajuaap.presentation.login.MvvmPresentation
import com.android.jijajuaap.ui.theme.White

@Composable
fun menuInitial(logingView: MvvmPresentation, navHostController: NavHostController){
    val colorEscogido = Colores()
    val fondo = Brush.verticalGradient(listOf(colorEscogido,Color.White ))
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
        bottomBar = {navigationBar(logingView,navHostController)},

    ){ innerPadding ->

        Column(
            Modifier.fillMaxSize().padding(innerPadding).background(fondo)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {


            }


        }
    }
}








@Composable
fun navigationBar(

    menuviewModel: MvvmPresentation,
    navHostController: NavHostController
) {
    NavigationBar(containerColor = White, modifier = Modifier.height(110.dp).border(2.dp, color = Color.Black,shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))){
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