package com.android.jijajuaap.ajustes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.presentation.login.MvvmPresentation
import com.android.jijajuaap.ui.theme.BLANCOeSP
import com.android.jijajuaap.ui.theme.rojoUser
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ajustes(
    menuUserMenuViewModel: UserMenuViewModel,
    memuView: MvvmPresentation,
    navHostController: NavHostController
){
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserUid) {
        if (currentUserUid != null) {
            menuUserMenuViewModel.loadUserData(currentUserUid)
        }
    }




    val user = menuUserMenuViewModel.user
    val colorEscogido = menuUserMenuViewModel.cambioColor(user?.team)
    val fondo = Brush.verticalGradient(listOf(colorEscogido, Color.White))

    Column(verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(fondo)) {

        Spacer(Modifier.weight(0.2f))

        Box(modifier = Modifier.padding(20.dp)){
           UpdateNameCard(menuUserMenuViewModel,colorEscogido)
        }


        Box(modifier = Modifier.padding(20.dp)) {
            ProfileInfoRow(
                R.drawable.cerrar_sesion,
                "Cerrar sesión",
                Color.White,
                1,
                memuView,
                navHostController,
                currentUserUid,
                menuUserMenuViewModel,


            )
        }
        Box(modifier = Modifier.padding(20.dp)) {
            ProfileInfoRow(
                R.drawable.dia_y_noche,
                "Modo Oscuro",
                Color.DarkGray,
                3,
                memuView,
                navHostController,
                currentUserUid,
                menuUserMenuViewModel,
            )
        }

        Box(modifier = Modifier.padding(20.dp)){
            ProfileInfoRow(
                R.drawable.eliminar,
                "Eliminar cuenta",
                rojoUser,
                2,
                memuView,
                navHostController,
                currentUserUid,
                menuUserMenuViewModel


            )
        }
        Spacer(Modifier.weight(1f))


    }
}

@Composable
fun ProfileInfoRow(
    icono: Int,
    label: String,
    red: Color,
    i: Int,
    memuView: MvvmPresentation,
    navHostController: NavHostController,
    currentUserUid: String?,
    menuUserMenuViewModel: UserMenuViewModel,



    ) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var colorLet by remember { mutableStateOf(Color.Black) }
    if (showDeleteDialog) {
        DeleteAccountDialog(
            onConfirm = {
                memuView.deleteCuentas(currentUserUid.toString())
                navHostController.popBackStack()
                navHostController.navigate(Routes.Screen1.routes)
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }



    Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                    when (i) {
                        1 -> {
                            memuView.logOut {
                                navHostController.navigate(Routes.Screen1.routes) {
                                    popUpTo(Routes.Screen1.routes) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        2 -> showDeleteDialog = true

                        3 -> {menuUserMenuViewModel.updateTeam("Secreto")
                            menuUserMenuViewModel.updateImg(R.drawable.error_de_usuario.toString())}

                    }
                }

            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = red)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if(red == Color.DarkGray){
                    colorLet = Color.White
                }else{
                    colorLet = Color.Black
                }
                Image(painterResource(id = icono), contentDescription = "", modifier = Modifier.size(25.dp),
                    colorFilter = ColorFilter.tint(colorLet))

                Text(label, style = MaterialTheme.typography.bodyMedium, color = colorLet, fontWeight = FontWeight.Bold)


        }
    }
}

@Composable
fun DeleteAccountDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Estás seguro?") },
        text = { Text("Se eliminará tu cuenta permanentemente. Esta acción no se puede deshacer.") },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                onConfirm()
            }) {
                Text("Sí, eliminar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun UpdateNameCard(menuUserMenuViewModel: UserMenuViewModel, colorEscogido: Color) {
    var name by remember { mutableStateOf("") }

    var colorLetras by remember { mutableStateOf(Color.Black) }
    if(colorEscogido != Color.DarkGray){
        colorLetras = Color.Black
    }else{
        colorLetras = Color.White
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Actualizar nombre",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nuevo nombre", color = Color.Black) },

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
                    ),
                )

                Button(
                    onClick = { menuUserMenuViewModel.updateName(name) },
                    colors = ButtonDefaults.buttonColors(containerColor = colorEscogido),shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cambiar nombre", color = colorLetras, fontWeight = FontWeight.Bold)
                }
            }



        }
    }
}




