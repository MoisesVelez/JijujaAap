package com.android.jijajuaap.menu


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.AutoMirrored.Rounded
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.ui.theme.White


@Composable
fun selecImagenAzu(userViewModel: UserMenuViewModel) {

    val usuario = userViewModel.user
    val colorEscogido = userViewModel.cambioColor(usuario?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))
    val imag = userViewModel.imagenUsuario(usuario)

    Column(modifier = Modifier.fillMaxSize().background(fondo).verticalScroll(rememberScrollState()).padding(25.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(imag),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, White, CircleShape),
            contentScale = ContentScale.Crop
        )

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
           Image(
                painter = painterResource(R.drawable.ilustracion_sin_titulo_2),
                contentDescription = "Avatar",
               modifier = Modifier
                   .clickable(onClick = {
                       userViewModel.updateImg("ilustracion_sin_titulo_2")
                   })
                   .weight(1f)
                   .aspectRatio(1f)
                   .padding(8.dp)
                   .clip(RoundedCornerShape(16.dp))
                   .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
               contentScale = ContentScale.Crop
           )

            Image(
                painter = painterResource(R.drawable.ilustracion_sin_titulo_4),
                contentDescription = "Avatar",  modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("ilustracion_sin_titulo_4")

                    })
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(R.drawable.emoticno6),
                contentDescription = "Avatar",
                modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticno6")
                        })
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.emoticono),
                contentDescription = "Avatar",  modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticono")})
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(R.drawable.emoticono2),
                contentDescription = "Avatar",
                modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticono2")})
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.emoticono5),
                contentDescription = "Avatar",  modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticono5")})
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(R.drawable.emoticono4),
                contentDescription = "Avatar",
                modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticono4")})
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.emoticono3),
                contentDescription = "Avatar",  modifier = Modifier
                    .clickable(onClick = {userViewModel.updateImg("emoticono3")})
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(4.dp, color = colorEscogido,shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

        }


    }


}

