package com.android.jijajuaap.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.jijajuaap.SplashScreen
import com.android.jijajuaap.presentation.SignUp.SignUpScreen
import com.android.jijajuaap.presentation.initial.InitialScreen
import com.android.jijajuaap.presentation.login.LoginScreen
import com.android.jijajuaap.presentation.login.MvvmPresentation



@Composable
fun NavigationWrapper() {
    val navHostController = rememberNavController()
    val loginViewModel: MvvmPresentation = hiltViewModel()

    NavHost(navController = navHostController, startDestination = Routes.SplashScreen.routes) {
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navHostController)
        }
        composable(Routes.Screen1.routes) {
            InitialScreen(navHostController)
        }
        composable(Routes.Screen2.routes) {
            LoginScreen(loginViewModel)
        }
        composable(Routes.Screen3.routes) {
            SignUpScreen()
        }
    }
}




