package com.android.jijajuaap.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController

import com.android.jijajuaap.SplashScreen
import com.android.jijajuaap.presentation.SignUp.SignUpScreen
import com.android.jijajuaap.presentation.initial.InitialScreen
import com.android.jijajuaap.presentation.login.LoginScreen
import com.android.jijajuaap.presentationMvvm.MvvmPresentation



@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Navigationwrapper(
    navHostController: NavHostController, ModelViewModelPresentation: MvvmPresentation,
) {

    NavHost(navController = navHostController,startDestination= Routes.SplashScreen.routes){
        composable(Routes.SplashScreen.routes) { SplashScreen(navHostController) }
        composable(Routes.Screen1.routes) { InitialScreen(navHostController) }
        composable(Routes.Screen2.routes) { LoginScreen(ModelViewModelPresentation) }
        composable(Routes.Screen3.routes) { SignUpScreen() }

    }
}


