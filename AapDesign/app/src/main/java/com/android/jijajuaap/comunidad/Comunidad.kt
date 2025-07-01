package com.android.jijajuaap.comunidad

import android.R.attr.label
import android.R.attr.value
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun menuInicialComunidad(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController){


    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        currentUserUid?.let {
            userMenuViewModel.loadUserData(it)
        }
    }



    val user = userMenuViewModel.user
    val imag = userMenuViewModel.imagenUsuario(user)
    val colorEscogido = userMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(Color.White,colorEscogido ))

    Scaffold(
        topBar = {barraTop(user,imag,navHostController,colorEscogido)}
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).background(fondo)) {


            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier.padding(10.dp).padding(bottom = 0.dp)) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Comunidad", color = Color.Black) },

                singleLine = true,
                modifier = Modifier.weight(1f).padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = colorEscogido,
                    focusedContainerColor = BLANCOeSP
                ),
            )

                Button(onClick = {}, modifier = Modifier.weight(0.3f),
                    colors = ButtonDefaults.buttonColors(colorEscogido),
                    shape = RoundedCornerShape(12.dp))
                {
                Text("Buscar", color =Color.Black, fontWeight = FontWeight.Bold)
            }
        }
            Card(modifier = Modifier.padding(20.dp)
                .fillMaxSize().clip(RoundedCornerShape(16.dp))
                .verticalScroll(rememberScrollState())
                .background(BLANCOeSP)
                .padding(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(15.dp)) {

                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("dinosaurios","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("dinosaurios","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("dinosaurios","Moises")
                    cardsComunidad("Futbol","Moises")
                    cardsComunidad("Futbol","Moises")
                }

            }

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
fun cardsComunidad(nombre: String, Usuario:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable(onClick = {})
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Quiz: ${nombre}", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.width(50.dp))
            Text("Creador: ${Usuario}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        }
    }
}
