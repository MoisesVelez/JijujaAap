package com.android.jijajuaap.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMenuViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadUserData(uid: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userData = authService.getUserData(uid)
                user = userData
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Error al cargar usuario"
            } finally {
                isLoading = false
            }
        }
    }
}
