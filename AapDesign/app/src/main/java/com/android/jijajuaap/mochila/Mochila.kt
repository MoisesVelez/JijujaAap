package com.android.jijajuaap.mochila

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.pintor.barra
import com.android.jijajuaap.pintor.barraBaja
import com.android.jijajuaap.pintor.pintorView
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun inventario(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,pintorView: pintorView) {


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
    val fondo = Brush.verticalGradient(listOf(Color.White, colorEscogido))

    Scaffold(
        topBar = { barra(user, imag, navHostController, colorEscogido) },
        bottomBar = { barraBaja(navHostController, White) }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(fondo)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("${user?.inventario}")

        }

    }
}