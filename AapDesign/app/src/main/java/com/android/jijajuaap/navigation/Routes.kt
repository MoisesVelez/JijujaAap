package com.android.jijajuaap.navigation

sealed class Routes(val routes:String) {
    object SplashScreen: Routes("splashscreen")
    object Screen1: Routes("initial")
    object Screen2: Routes("login")
    object Screen3: Routes("sign")
    object Menu1:Routes("menu1")
    object MenuUser:Routes("menuUsuario")
    object MenuImagen:Routes("menuImagen")
    object MenuImagenVer:Routes("menuImagenVer")
    object MenuImagenAzu:Routes("menuImagenAzu")
    object menuPartidaPublica:Routes("menuPartidaPublica")
}