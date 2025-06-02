package com.android.jijajuaap


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.menuviewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.initial.Colores
import com.android.jijajuaap.ui.theme.PurpleGrey40
import com.android.jijajuaap.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController,menuviewModel: menuviewModel) {

    LaunchedEffect(key1 = true) {
        delay(2200)
        navHostController.popBackStack()
       val ruta = menuviewModel.checkDestinantion()
        navHostController.navigate(ruta)
    }
    Splash()
}

@Composable
fun Splash(){
    Column(modifier = Modifier.fillMaxSize().background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.initialscreen),
            contentDescription = "SplashScreen",
            modifier = Modifier.size(200.dp)
                .border(4.dp, Colores(),shape = RoundedCornerShape(16.dp)))

        Spacer(modifier = Modifier.size(25.dp))



        Text(
            text = "JIJAJU",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleGrey40
        )
        LinearProgressIndicator(color = Colores(), trackColor =Colores(), strokeCap = StrokeCap.Round)


    }
}