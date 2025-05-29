package com.android.jijajuaap.presentation.login

import android.annotation.SuppressLint
import android.util.Log
import android.util.Patterns
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.initial.Colores
import com.android.jijajuaap.ui.theme.White


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(LoginViewModel: MvvmPresentation, navHostController: NavHostController) {
    val colorEscogido = Colores()
    var comprobante: Boolean by remember { mutableStateOf(false) }
    val isLoading by LoginViewModel.isLoading.collectAsState()
    var contrasenaVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(White, colorEscogido)))
            .padding(15.dp).verticalScroll(rememberScrollState())
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
//--------------------
        Spacer(modifier = Modifier.size(150.dp))
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp),
                color = colorEscogido
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
//-----------------------
        Text("Email o usuario", fontWeight = FontWeight.Bold,fontSize = 20.sp,color = Color.Black)
//----------------------
        TextField(value = LoginViewModel.email,
            onValueChange = { LoginViewModel.email = it},
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 32.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = White,
                focusedContainerColor = colorEscogido))

        Spacer(modifier = Modifier.size(15.dp))

//-----------------------
        Text("Contraseña", fontWeight = FontWeight.Bold,fontSize = 20.sp,color = Color.Black)
//--------------------
        TextField(value = LoginViewModel.password,
            onValueChange = {LoginViewModel.password= it},
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 32.dp),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = White,
                focusedContainerColor = colorEscogido),
            visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (contrasenaVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                    Icon(imageVector = image, contentDescription = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = Color.Black)
                }
            })
        Spacer(modifier = Modifier.size(60.dp))

        texto1(comprobante)
        //---------------------------------

        Button(
            onClick = {
                val email = LoginViewModel.email.trim()
                val password = LoginViewModel.password.trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Log.e("LoginScreen", "Email or password is empty")

                    return@Button
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Log.e("LoginScreen", "Email format is invalid")
                    comprobante= true
                    return@Button
                }
                LoginViewModel.login(email,password,
                   onSuccess = {navHostController.navigate(Routes.Menu1.routes)},
                    onError = {}
                )

               }
                    ,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorEscogido),

            ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.la_mejor_experiencia_del_cliente),
                    contentDescription = "Entrar",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(35.dp)
                )

                Text(
                    text = "Entrar",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier = Modifier.size(15.dp))
        Text("¿Olvidaste la contraseña?",
            modifier = Modifier.clickable(onClick = {}),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
        //--------------------------
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
fun texto1(comprobante:Boolean){
    if(comprobante){
        Text(
            text = "Email o contraseña incorrectos",
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


