package com.android.jijajuaap.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.jijajuaap.SplashScreen
import com.android.jijajuaap.menu.UserMenuViewModel
import com.android.jijajuaap.menu.menu
import com.android.jijajuaap.menu.menuInitial
import com.android.jijajuaap.menu.menuviewModel
import com.android.jijajuaap.menu.selecImagen
import com.android.jijajuaap.menu.selecImagenAzu
import com.android.jijajuaap.menu.selecImagenVer
import com.android.jijajuaap.partidaPublica.QuizScreen
import com.android.jijajuaap.partidaPublica.QuizViewModel
import com.android.jijajuaap.partidaPublica.gmaplayViewModel
import com.android.jijajuaap.partidaPublica.gmaplayViewModel_Factory
import com.android.jijajuaap.partidaPublica.menuPartidaPublica
import com.android.jijajuaap.partidaPublica.roadMap
import com.android.jijajuaap.presentation.SignUp.SignUpScreen
import com.android.jijajuaap.presentation.SignUp.SignUpViewModel
import com.android.jijajuaap.presentation.initial.InitialScreen
import com.android.jijajuaap.presentation.initial.InitialViewModel
import com.android.jijajuaap.presentation.login.LoginScreen
import com.android.jijajuaap.presentation.login.MvvmPresentation



@Composable
fun NavigationWrapper() {
    val navHostController = rememberNavController()
    val loginViewModel: MvvmPresentation = hiltViewModel()
    val signViewModel: SignUpViewModel = hiltViewModel()
    val menuViewModel: menuviewModel = hiltViewModel()
    val initViewModel: InitialViewModel = hiltViewModel()
    val userViewModel: UserMenuViewModel = hiltViewModel()
    val gameRoad: gmaplayViewModel = hiltViewModel()
    val gameBasic: QuizViewModel = hiltViewModel()


    NavHost(navController = navHostController, startDestination = Routes.SplashScreen.routes) {
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navHostController,menuViewModel)
        }
        composable(Routes.Screen1.routes) {
            InitialScreen(navHostController,initViewModel)
        }
        composable(Routes.Screen2.routes) {
            LoginScreen(loginViewModel,navHostController)
        }
        composable(Routes.Screen3.routes) {
            SignUpScreen(signViewModel,navHostController,loginViewModel)
        }
        composable(Routes.Menu1.routes) {
            menuInitial(loginViewModel,navHostController,userViewModel)
        }
        composable(Routes.MenuUser.routes) {
            menu(userViewModel,navHostController)
        }
        composable(Routes.MenuImagen.routes) {
            selecImagen(userViewModel)
        }
        composable(Routes.MenuImagenVer.routes) {
            selecImagenVer(userViewModel)
        }
        composable(Routes.MenuImagenAzu.routes) {
            selecImagenAzu(userViewModel)
        }
        composable(Routes.menuPartidaPublica.routes) {
            menuPartidaPublica(userViewModel,navHostController,loginViewModel)
        }
        composable(Routes.menuRoadMap.routes) {
            roadMap(userViewModel,gameRoad,navHostController)
        }
        composable(Routes.menuPartidaBasica.routes) {
            QuizScreen(gameBasic,userViewModel)
        }

    }
}




