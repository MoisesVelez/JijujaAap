package com.android.jijajuaap.presentation.initial

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.ui.theme.Pink40
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.azulJi
import com.android.jijajuaap.ui.theme.rojoJa
import com.android.jijajuaap.ui.theme.verdeJu


import kotlin.random.Random


@Composable
fun InitialScreen(navHostController: NavHostController){
    val colorEscogido = Colores()
    Column(modifier = Modifier.fillMaxSize()
        .background(Brush.verticalGradient(listOf(White,colorEscogido)))
        .padding(15.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(60.dp))
        Image(
            painter = painterResource(id = R.drawable.portada),
            contentDescription = "Logo App",
            modifier = Modifier.size(200.dp)
                .clip(CircleShape)
                .border(4.dp, colorEscogido, CircleShape)
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {navHostController.navigate(Routes.Screen3.routes)},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorEscogido),

            )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.exito),
                    contentDescription = "Registrarse",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(35.dp)
                )

                Text(
                    text = "Registrarse",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier =Modifier.height(10.dp))

        Button(onClick = {},
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = White),
            //border = BorderStroke(2.dp, Color.Black)
        )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.google_chrome),
                    contentDescription = "Registrarse google",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(35.dp)
                )

                Text(
                    text = "Google",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier =Modifier.height(15.dp))
        Text("Iniciar sesion", color = Color.Black ,fontWeight = FontWeight.Bold, modifier = Modifier.clickable(onClick = {navHostController.navigate(Routes.Screen2.routes)}))


        Spacer(modifier = Modifier.weight(2f))

    }
}

@Composable
fun Colores(): Color {
    val colorElegido:Color
    val randomNumber = remember { Random.nextInt(0,3 ) }
    colorElegido = when(randomNumber){
        0 -> verdeJu
        1 -> rojoJa
        2 -> azulJi
        else -> Pink40
    }
    return colorElegido

}