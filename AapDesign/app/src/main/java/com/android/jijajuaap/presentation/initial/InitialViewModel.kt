package com.android.jijajuaap.presentation.initial

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class InitialViewModel @Inject constructor(internal val authService: AuthService):ViewModel(){

    fun onGoogleLoginSelected(googleLauncherLogin:(GoogleSignInClient) -> Unit){
        val gsc = authService.getGoogleClient()
        googleLauncherLogin(gsc)
    }

    fun loginWithGoogle(string: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                authService.loginWithGoogle(string)
            }
            if (result != null){
                onSuccess()
            }
        }
    }

    fun TwitterLoginSelected(
        activity: Activity,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    authService.loginWithTwitter(activity)
                }
                if (result != null) {
                    onSuccess()
                } else {
                    onError(Exception("Twitter login returned null user"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }




}


