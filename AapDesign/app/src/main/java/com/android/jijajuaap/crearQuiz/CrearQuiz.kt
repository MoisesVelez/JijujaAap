package com.android.jijajuaap.crearQuiz

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CrearQuiz(userViewModel: UserMenuViewModel, navHostController: NavHostController,crearQuizView: crearQuizView) {


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userViewModel.loadUserData(it)
        }
    }
    val user = userViewModel.user

    val imag = userViewModel.imagenUsuario(user)
    val colorEscogido = userViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White,colorEscogido ))
    val focusManager = LocalFocusManager.current
    val blanco50 = Color.White.copy(alpha = 0.5f)



    var title = crearQuizView.titulO
    crearQuizView.autOr = user?.name.toString()





    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)}
    )
    {innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)

            .fillMaxSize()
            .background(fondo)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            focusManager.clearFocus()
        },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(5.dp))
            Text("Creación de Quiz", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.size(15.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(start = 16.dp, end = 16.dp)
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                    Image(painterResource(R.drawable.firma), contentDescription = "Titulo")
                    TextField(
                        value = title,
                        onValueChange = {title=it
                                        crearQuizView.titulO=it},
                        label = { Text("Titulo Quiz", color = Color.Black) },

                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = BLANCOeSP,
                            focusedContainerColor = BLANCOeSP
                        ))
                }

            }

            Spacer(modifier = Modifier.size(7.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .padding(16.dp)
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(painterResource(R.drawable.mencionar), contentDescription = "Titulo")
                    TextField(
                        value = "Autor: ${user?.name ?: ""}",
                        onValueChange = {},
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = BLANCOeSP,
                            focusedContainerColor = BLANCOeSP
                        ),
                        readOnly = true,
                        singleLine = true
                    )


                }

            }
            Spacer(modifier = Modifier.size(10.dp))
            Card(modifier = Modifier.fillMaxWidth().height(680.dp).padding(16.dp)
            , elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorEscogido)) {

                Column(modifier = Modifier.verticalScroll(rememberScrollState())
                    .padding(10.dp)
                    .fillMaxSize()) {
                    crearPreguntas(colorEscogido,crearQuizView)

                }

            }
            Spacer(modifier = Modifier.size(20.dp))


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraTop(
    user: User?,
    imag: Int,
    navHostController: NavHostController,
    colorEscogido: Color
) {

    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = imag),
                contentDescription = "Logo App",
                modifier = Modifier.size(75.dp).padding(5.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.White, CircleShape)
                    .clickable(onClick = { navHostController.navigate(Routes.MenuUser.routes) })
                    .background(color = Color.White),

                )
        },
        modifier = Modifier.height(125.dp),
        colors = TopAppBarDefaults.topAppBarColors(colorEscogido),
        actions = {
            Text(
                user?.name.toString(), modifier =
                    Modifier.padding(25.dp), fontWeight = FontWeight.Bold, color = colorLetras
            )

            Text(
                "Puntos totales: ${user?.totalPoints}", modifier =
                    Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = colorLetras
            )


        })

}


@Composable
fun crearPreguntas(
    color: Color,
    crearQuizView: crearQuizView,

    ){
    var pregunta:String by remember { mutableStateOf("") }

    var opcion1 by remember { mutableStateOf("") }
    var opcion2 by remember { mutableStateOf("") }
    var opcion3 by remember { mutableStateOf("") }
    var opcion4 by remember { mutableStateOf("") }



    var opciones = listOf(opcion1, opcion2, opcion3,opcion4)
    var correcto: Int by remember { mutableIntStateOf(0) }
    var numeroPreguntas by remember { mutableIntStateOf(0) }

    Text("Cantidad de preguntas: ${numeroPreguntas}", fontWeight = FontWeight.Bold, color = Color.Black)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(start = 16.dp, end = 16.dp)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = pregunta,
                onValueChange = {pregunta = it},
                label = { Text("Pregunta", color = Color.Black) },

                singleLine = true,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = BLANCOeSP,
                    focusedContainerColor = BLANCOeSP
                ))
        }}

    Spacer(modifier = Modifier.size(15.dp))
    Divider(
        color = White,
        thickness = 3.dp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(start = 16.dp, end = 16.dp)
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)) {

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    value = opcion1,
                    onValueChange = {opcion1=it},
                    label = { Text("1.", color = Color.Black) },

                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = BLANCOeSP,
                        focusedContainerColor = BLANCOeSP
                    ))
            }}
    Spacer(modifier = Modifier.size(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(start = 16.dp, end = 16.dp)
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    TextField(
                        value = opcion2,
                        onValueChange = {opcion2=it},
                        label = { Text("2.", color = Color.Black) },

                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = BLANCOeSP,
                            focusedContainerColor = BLANCOeSP
                        )
                    )
                }
            }
    Spacer(modifier = Modifier.size(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = opcion3,
                            onValueChange = {opcion3=it},
                            label = { Text("3.", color = Color.Black) },

                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = BLANCOeSP,
                                focusedContainerColor = BLANCOeSP
                            )
                        )
                    }
                }
    Spacer(modifier = Modifier.size(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = opcion4,
                            onValueChange = {opcion4=it},
                            label = { Text("4.", color = Color.Black) },

                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = BLANCOeSP,
                                focusedContainerColor = BLANCOeSP
                            )
                        )

                    }
                }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(105.dp)
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Button(onClick = {

            if(pregunta.toString().equals("")){
                crearQuizView.subirQuiz()
                crearQuizView.borrarPreguntas()
                crearQuizView.titulO = ""
                numeroPreguntas =0
            }else{

            var preguntaTes:test = test()
            preguntaTes.pregunta=pregunta
            preguntaTes.opciones=opciones
            preguntaTes.correctAnswerIndex=correcto

            crearQuizView.añadirPregunta(preguntaTes)

            pregunta = ""
            opcion1=""
            opcion2=""
            opcion3=""
            opcion4=""
            correcto=0

            crearQuizView.subirQuiz()
                crearQuizView.borrarPreguntas()
                crearQuizView.titulO = ""
                numeroPreguntas =0}}
            ,colors = ButtonDefaults.buttonColors(White),
            shape = RoundedCornerShape(12.dp))  {
            Text("Subir",fontWeight = FontWeight.Bold, color = Color.Black)
        }




        Button(onClick = {var preguntaTes:test = test()
        preguntaTes.pregunta=pregunta
        preguntaTes.opciones=opciones
        preguntaTes.correctAnswerIndex=correcto

            crearQuizView.añadirPregunta(preguntaTes)

            pregunta = ""
            opcion1=""
            opcion2=""
            opcion3=""
            opcion4=""
            correcto=0
        numeroPreguntas +=1
        },colors = ButtonDefaults.buttonColors(White),
            shape = RoundedCornerShape(12.dp)) {
            Text("Otra pregunta", fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }

            }




