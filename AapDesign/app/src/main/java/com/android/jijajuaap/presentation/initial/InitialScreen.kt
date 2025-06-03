package com.android.jijajuaap.presentation.initial

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException


import kotlin.random.Random


@Composable
fun InitialScreen(navHostController: NavHostController,InitialViewModel: InitialViewModel) {
    val colorEscogido = Colores()
    val context = LocalContext.current
    val googleSignInClient = remember { InitialViewModel.authService.getGoogleClient() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken

                if (idToken != null) {
                    InitialViewModel.loginWithGoogle(
                        idToken,
                        onSuccess = {navHostController.navigate(Routes.Menu1.routes)},
                        onError = {}
                    )
                } else {
                    Log.e("Login", "ID Token is null")
                }

            } catch (e: ApiException) {
                Log.e("Login", "Google sign in failed", e)
            }
        } else {
            Log.w("Login", "Sign in canceled")
        }
    }


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

        Button(onClick = {googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)}
        },
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = White),

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
                    text = "Cuenta de Google",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier =Modifier.height(15.dp))
        Button(onClick = {},
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = White),

            )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Registrarse facebook",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(35.dp)
                )

                Text(
                    text = "Cuenta de Facebook",
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