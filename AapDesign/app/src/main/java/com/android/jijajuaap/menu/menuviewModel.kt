package com.android.jijajuaap.menu

import androidx.lifecycle.ViewModel

import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class menuviewModel @Inject constructor(private val authService: AuthService): ViewModel(){

    private fun isUserLogged():Boolean{
        return authService.isUserLogged()
    }


    fun checkDestinantion(): String {
        val isUserLoged = isUserLogged()
        if(isUserLoged){
           return Routes.Menu1.routes
        }else{
            return  Routes.Screen1.routes
        }

    }





}