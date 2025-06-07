package com.android.jijajuaap.presentation.SignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val authService: AuthService): ViewModel(){

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = withContext(Dispatchers.IO) {
                    authService.register(email, password)
                }
                if (user != null) {
                    withContext(Dispatchers.IO) {
                        authService.saveUserInFirestore(user, name)
                    }
                    onSuccess()
                } else {
                    onError()
                }
            } catch (e: Exception) {
                if (e is FirebaseAuthUserCollisionException) {
                    onError()
                } else {
                    onError()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun CoroutineScope.onError() {

    }


}