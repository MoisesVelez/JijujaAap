package com.android.jijajuaap.pintor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.android.jijajuaap.R
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.navigation.Routes
import com.android.jijajuaap.objects.Objetos
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.ui.theme.White
import androidx.compose.foundation.lazy.grid.items
import com.android.jijajuaap.ui.theme.colorCrema
import com.android.jijajuaap.ui.theme.rojoUser
import com.android.jijajuaap.ui.theme.verdeUser
import com.google.firebase.auth.FirebaseAuth

@Composable
fun pintorObjetos(userMenuViewModel: UserMenuViewModel,navHostController: NavHostController,pintorView: pintorView) {

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
    val fondo = Brush.verticalGradient(listOf(Color.White, colorCrema))
    var objeto = pintorView.listaObjetos
    var itemPequeño = pintorView.objeto
    var comprobante by remember { mutableStateOf(false) }


    if (comprobante == true){
        infoObjetos(
            onDismiss = {comprobante=false},
            comprar = {pintorView.comprarObjeto(user?.uid.toString(),itemPequeño)

                      }
            ,itemPequeño,
            pintorView,user)
    }
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

            Spacer(modifier = Modifier.size(15.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = R.drawable.portada),
                        contentDescription = "",
                        modifier = Modifier.size(130.dp).padding(15.dp)
                    )
                    Text(
                        "Bienvenido al pintor, aqui podrás cambiar tus puntos por pasivas" +
                                " que te ayudarán en tus preguntas.",
                        modifier = Modifier.padding(15.dp),
                        color = Color.Black
                    )
                }


            }
            Card(
                modifier = Modifier.width(100.dp).height(100.dp)
                    .padding(15.dp)
                    .clickable(onClick = {navHostController.navigate(Routes.mochila.routes)}),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = colorEscogido)
            ) {
                Image(
                    painterResource(id = R.drawable.mochila),
                    contentDescription = "",
                    modifier = Modifier.padding(5.dp)
                )

            }


            Spacer(modifier = Modifier.size(15.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)


            ) {

                items(objeto) { objeto ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.9f)
                            .clickable(onClick = { pintorView.obtenerObjeto(objeto.nombre)
                                comprobante = true})
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = pintorView.imagenObjeto(objeto)),
                                contentDescription = objeto.nombre,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Text(
                                text = objeto.nombre,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 5.dp)
                            )

                            Text(
                                text = "Coste: ${objeto.coste}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }



        }

    }
}



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun barra(
        user: User?,
        imag: Int,
        navHostController: NavHostController,
        colorEscogido: Color
    ) {

        var colorLetras by remember { mutableStateOf(Color.Black) }
        if (colorEscogido != Color.DarkGray) {
            colorLetras = Color.Black
        } else {
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
                        Modifier.padding(25.dp), fontWeight = FontWeight.Normal, color = colorLetras
                )

                Text(
                    "PT: ${user?.totalPoints}", modifier =
                        Modifier.padding(10.dp), fontWeight = FontWeight.Bold, color = colorLetras
                )


            })

    }


@Composable
fun barraBaja(
    navHostController: NavHostController,
    colorEscogido: Color
) {
    val colorLetras = if (colorEscogido != Color.DarkGray) Color.Black else Color.White

    NavigationBar(
        containerColor = colorEscogido,
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navHostController.navigate(Routes.Menu1.routes) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.buscar_casa),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )

        NavigationBarItem(
            selected = false,
            onClick = { navHostController.navigate(Routes.menuInicioComunidad.routes) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.pueblo),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* Acción social */ },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.amistad),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = colorLetras
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorLetras,
                unselectedIconColor = colorLetras,
                indicatorColor = Color.Transparent
            ),
            label = null
        )
    }
}

@Composable
fun infoObjetos(
    onDismiss: () -> Unit,
    comprar: () -> Unit,
    objetos: Objetos?,
    pintorView: pintorView,
    user: User?

) {
    val imagen = pintorView.imagenObjeto(objetos)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = objetos?.nombre ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = imagen),
                    contentDescription = objetos?.nombre,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {}
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = objetos?.descripcion ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Coste: ${objetos?.coste}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                if(user?.inventario?.contains(objetos) == true){
                    Text("Objeto comprado", fontWeight =  FontWeight.Bold, color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(rojoUser),
                    shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(3.dp, Color.Black)
                    ) {
                        Image(
                            painterResource(id = R.drawable.x),
                            contentDescription = "",
                            modifier = Modifier.padding(5.dp).size(30.dp)
                        )
                    }

                    var colores = rojoUser
                    if(user?.inventario?.contains(objetos) == true){
                        colores
                    }else{
                        colores = verdeUser
                    }
                    Button(onClick = {
                        comprar()
                        onDismiss()

                    },
                        colors = ButtonDefaults.buttonColors(colores),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(3.dp, Color.Black)

                    ) {
                        Image(
                            painterResource(id = R.drawable.pago_por_clic),
                            contentDescription = "",
                            modifier = Modifier.padding(5.dp).size(30.dp)

                        )
                    }
                }
            }
        }
    }
}



