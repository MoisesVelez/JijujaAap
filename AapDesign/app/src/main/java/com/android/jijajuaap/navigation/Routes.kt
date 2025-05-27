package com.android.jijajuaap.navigation

sealed class Routes(val routes:String) {
    object SplashScreen: Routes("splashscreen")
    object Screen1: Routes("initial")
    object Screen2: Routes("login")
    object Screen3: Routes("sign")
}