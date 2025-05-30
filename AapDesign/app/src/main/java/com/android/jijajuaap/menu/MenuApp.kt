package com.android.jijajuaap.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.jijajuaap.R
import com.android.jijajuaap.presentation.initial.Colores
import com.android.jijajuaap.ui.theme.White

@Composable
fun menuInitial(){
    val colorEscogido = Colores()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {navigationBar(colorEscogido)},

    ){ innerPadding ->

        Column(
            Modifier.fillMaxSize().background(White).padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



        }
    }
}

@Composable
fun navigationBar(colorEscogido: Color) {
    NavigationBar(containerColor = colorEscogido){
        NavigationBarItem(selected = true,
            onClick = {},
            icon = {Icon(painter = painterResource(R.drawable.persona),modifier= Modifier.size(20.dp), contentDescription = "")}
            ,label = { Text("Usuario") })

        NavigationBarItem(selected = true,
            onClick = {},
            icon = {Icon(painter = painterResource(R.drawable.persona),modifier= Modifier.size(20.dp), contentDescription = "")}
            ,label = { Text("Crear") })

        NavigationBarItem(selected = true,
            onClick = {},
            icon = {Icon(painter = painterResource(R.drawable.persona),modifier= Modifier.size(20.dp), contentDescription = "")}
            ,label = { Text("Lore") }
        )

    }
}