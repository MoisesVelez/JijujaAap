package com.android.jijajuaap.presentation.login

import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MvvmPresentation @Inject constructor(private val authService: AuthService) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: FirebaseUser? = withContext(Dispatchers.IO) {
                    authService.login(email, password)
                }
                if (result != null) {
                    onSuccess()
                } else {
                    onError("Error de autenticación. Verifica tus datos.")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error logging in", e)
                onError("Ha ocurrido un error al iniciar sesión.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logOut(navigateToLogin:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logOut()
        }
        navigateToLogin()
        email = ""
        password = ""
    }
}